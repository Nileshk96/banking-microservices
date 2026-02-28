pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven3'
        git 'git'
    }

    stages {

        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build Account Service') {
            steps {
                dir('account-service') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Customer Service') {
            steps {
                dir('customer-service') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Transaction Service') {
            steps {
                dir('transaction-service') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build API Gateway') {
            steps {
                dir('api-gateway') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Eureka Server') {
            steps {
                dir('eureka-server') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }
    }

    post {
        success {
            echo 'All microservices built successfully!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
