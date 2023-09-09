pipeline {
    agent any

	tools { 
        maven 'Maven 3' 
    }
   
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

         stage('Log info') {
            steps {
                sh 'echo $JAVA_HOME'
                sh 'echo $MAVEN_HOME'
                sh 'java --version'
                sh 'mvn --version'
                
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
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






