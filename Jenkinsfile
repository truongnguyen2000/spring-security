pipeline {
    agent any


    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn install'
            }
        }

        stage('Build and Deploy Module 1') {
            steps {
                dir('com.truong.oauth') {
                    sh 'docker compose -f docker-compose.yml up --build -d'
                }
            }
        }

        stage('Build and Deploy Module 2') {
            steps {
                dir('com.truong.restapi') {
                   sh 'docker compose -f docker-compose.yml up --build -d'
                }
            }
        }
    }

    post {
        success {
            echo 'Deployment successful'
        }

        failure {
            echo 'Deployment failed'
        }
    }
}






