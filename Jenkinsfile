pipeline {
    agent any

    environment {
        AWS_ACCOUNT_ID = '058264367850'
        AWS_DEFAULT_REGION = 'us-east-1'
        ECR_REPO_NAME = 'public.ecr.aws/microservice'
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
                    docker.build("${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${ECR_REPO_NAME}:${IMAGE_TAG}")
                }
            }
        }

        stage('Login to AWS ECR') {
            steps {
                script {
                    sh "aws ecr get-login-password --region ${AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com"
                }
            }
        }

        stage('Push Docker Image to ECR') {
            steps {
                script {
                    sh "docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${ECR_REPO_NAME}:${IMAGE_TAG}"
                }
            }
        }

        stage('Deploy to Elastic Beanstalk') {
            steps {
                script {
                    sh """
                    aws elasticbeanstalk create-application-version --application-name ${APP_NAME} --version-label ${IMAGE_TAG} --source-bundle S3Bucket="${AWS_ACCOUNT_ID}-elasticbeanstalk-s3-bucket",S3Key="docker/${ECR_REPO_NAME}:${IMAGE_TAG}"
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

