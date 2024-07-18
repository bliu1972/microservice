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
                    // docker.build("${ECR_REPO_URL}/${APP_NAME}:${IMAGE_TAG}")
                    docker.build("${APP_NAME}:${IMAGE_TAG}")
                }
            }
        }

        stage('Login to AWS ECR Public') {
            steps {
                script {
                    sh "aws ecr-public get-login-password --region ${AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin public.ecr.aws"
                }
            }
        }

        stage('Push Docker Image to ECR Public') {
            steps {
                script {
                    sh "docker tag ${APP_NAME}:${IMAGE_TAG} ${ECR_REPO_URL}/${APP_NAME}:${IMAGE_TAG}"
                    sh "docker push ${ECR_REPO_URL}/${APP_NAME}:${IMAGE_TAG}"
                }
            }
        }

        stage('Deploy to Elastic Beanstalk') {
            steps {
                script {
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

