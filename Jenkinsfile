pipeline {
    agent any

    environment {
        AWS_DEFAULT_REGION = 'us-east-1' // Update to your AWS region
        ECR_REPO_URL = 'public.ecr.aws/t4e5x6y4/microservice' // Update with your public ECR repository URL
        IMAGE_TAG = "${env.BUILD_ID}"
        APP_NAME = 'mymicroservice'
        GIT_REPO_URL = 'git@github.com:bliu1972/microservice.git' // Update with your repository URL
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
                    sh "aws ecr-public get-login-password --region ${AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin public.ecr.aws/t4e5x6y4"
                }
            }
        }

        stage('Tag and Push Docker Image') {
            steps {
                script {
                    // Tag the Docker image for ECR Public
                    sh "docker tag ${APP_NAME}:${IMAGE_TAG} ${ECR_REPO_URL}/${APP_NAME}:${IMAGE_TAG}"
                    // Push the Docker image to ECR Public
                    // sh "docker push ${ECR_REPO_URL}/${APP_NAME}:${IMAGE_TAG}"
                    sh "docker push ${ECR_REPO_URL}:${IMAGE_TAG}"
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Here, you would include deployment steps such as updating Elastic Beanstalk
                    // Replace with appropriate deployment commands or scripts
                    echo "Deployment steps would go here."
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

