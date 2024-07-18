pipeline {
    agent any

    environment {
        AWS_DEFAULT_REGION = 'us-east-1'
        ECR_REPO_URL = 'public.ecr.aws/microservice' // Update with your public ECR repository URL
        IMAGE_TAG = "${env.BUILD_ID}"
        APP_NAME = 'mymicroservice'
        ENV_NAME = 'Mymicroservice-env'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'git@github.com:bliu1972/microservice.git'
            }
        }

        stage('Build with Maven') {
            steps {
                script {
                    sh "mvn clean package -Pskip-tests"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${ECR_REPO_URL}/${APP_NAME}:${IMAGE_TAG}")
                }
            }
        }

        stage('Login to AWS ECR Public') {
            steps {
                script {
                    // Log in to AWS ECR Public
                    sh "aws ecr-public login --region ${AWS_DEFAULT_REGION}"
                }
            }
        }

        stage('Push Docker Image to ECR Public') {
            steps {
                script {
                    // Tag and push the Docker image to public ECR
                    sh "docker tag ${APP_NAME}:${IMAGE_TAG} ${ECR_REPO_URL}/${APP_NAME}:${IMAGE_TAG}"
                    sh "docker push ${ECR_REPO_URL}/${APP_NAME}:${IMAGE_TAG}"
                }
            }
        }

        stage('Deploy to Elastic Beanstalk') {
            steps {
                script {
                    // Create a new application version and deploy to Elastic Beanstalk
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
            cleanWs()
        }
    }
}

