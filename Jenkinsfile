pipeline {
    agent any

    environment {
        AWS_DEFAULT_REGION = 'us-east-1' // Update to your AWS region
        ECR_REPO_URL = 'public.ecr.aws/t4e5x6y4' // Update with your public ECR repository URL
        IMAGE_TAG = "${env.BUILD_ID}"
        APP_NAME = 'microservice'
        GIT_REPO_URL = 'git@github.com:bliu1972/microservice.git' // Update with your repository URL
        ENV_NAME = 'Mymicroservice-env'
	AWS_ACCOUNT_ID=058264367850
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from GitHub
                git branch: 'main', url: "${GIT_REPO_URL}"
            }
        }

        stage('Build with Maven') {
            steps {
                script {
                    // Build the Maven project
                    sh "mvn clean package -DskipTests"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image with a tag based on build ID
                    // docker.build("${ECR_REPO_URL}/${APP_NAME}:${IMAGE_TAG}")
                    docker.build("${APP_NAME}:${IMAGE_TAG}")
                }
            }
        }

        stage('Login to AWS ECR Public') {
            steps {
                script {
                    // Login to AWS ECR Public
                    sh "aws ecr-public get-login-password --region ${AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${ECR_REPO_URL}"
                }
            }
        }

        stage('Tag and Push Docker Image') {
            steps {
                script {
                    // Tag the Docker image for ECR Public
                    sh "docker tag ${APP_NAME}:${IMAGE_TAG} ${ECR_REPO_URL}/${APP_NAME}:${IMAGE_TAG}"
                    // Push the Docker image to ECR Public
                    sh "docker push ${ECR_REPO_URL}/${APP_NAME}:${IMAGE_TAG}"
                }
            }
        }

        stage('Deploy to Elastic Beanstalk') {
            steps {
                script {
                    /*
                     * Create a new application version in Elastic Beanstalk
                     * Update the environment to use the new version
                     */
                    sh """
                    aws elasticbeanstalk create-application-version --application-name ${APP_NAME} --version-label ${IMAGE_TAG} --source-bundle S3Bucket="${AWS_ACCOUNT_ID}-elasticbeanstalk-s3-bucket",S3Key="docker/${APP_NAME}:${IMAGE_TAG}"
                    aws elasticbeanstalk update-environment --application-name ${APP_NAME} --environment-name ${ENV_NAME} --version-label ${IMAGE_TAG}
                    """
                }
            }
        }
    }

    post {
        always {
            // Clean workspace after the build
            cleanWs()
        }
    }
}

