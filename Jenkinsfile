pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'sontran0102/test_docker_repo'
        DOCKER_TAG = 'latest'
        CONTAINER_NAME = 'todo-app'
        APP_PORT = '8081'
    }

    stages {
        stage('Checkout') {
            steps {
                echo "hello"
                git branch: 'main', url: 'https://github.com/SonTran0102/todo-app-java.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE:$DOCKER_TAG .'
            }
        }

        stage('Login to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-hub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                sh 'docker push $DOCKER_IMAGE:$DOCKER_TAG'
            }
        }

        stage('Deploy and Run App') {
            agent { label 'ubuntu-slave' }

            steps {
                script {
                    echo "Deploying container on slave..."
                    sh """
                        docker rm -f $CONTAINER_NAME || true
                        docker pull $DOCKER_IMAGE:$DOCKER_TAG
                        docker run -d --name $CONTAINER_NAME -p $APP_PORT:$APP_PORT $DOCKER_IMAGE:$DOCKER_TAG
                    """
                }
            }
        }
    }

    post {
        success {
            echo 'Docker image built, pushed, and deployed successfully!'
        }
        failure {
            echo 'Failed. Check the logs for details.'
        }
    }
}
