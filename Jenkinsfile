pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven3'
        git 'git'
    }

    environment {
        DOCKERHUB_REPO = "yourdockerhub"
        BUILD_TAG = "${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build All Microservices') {
            parallel {

                stage('Account Service') {
                    steps {
                        dir('account-service') {
                            sh 'mvn clean package -DskipTests'
                        }
                    }
                }

                stage('Customer Service') {
                    steps {
                        dir('customer-service') {
                            sh 'mvn clean package -DskipTests'
                        }
                    }
                }

                stage('Transaction Service') {
                    steps {
                        dir('transaction-service') {
                            sh 'mvn clean package -DskipTests'
                        }
                    }
                }

                stage('API Gateway') {
                    steps {
                        dir('api-gateway') {
                            sh 'mvn clean package -DskipTests'
                        }
                    }
                }

                stage('Eureka Server') {
                    steps {
                        dir('eureka-server') {
                            sh 'mvn clean package -DskipTests'
                        }
                    }
                }
            }
        }

        stage('SonarQube Scan - Account Service') {
            steps {
                dir('account-service') {
                    withSonarQubeEnv('sonar-server') {
                        withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                            sh '''
                                mvn sonar:sonar \
                                -Dsonar.projectKey=account-service \
                                -Dsonar.login=$SONAR_TOKEN
                            '''
                        }
                    }
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds',
                        usernameVariable: 'USER',
                        passwordVariable: 'PASS')]) {

                    sh 'echo $PASS | docker login -u $USER --password-stdin'

                    sh '''
                        docker build -t $DOCKERHUB_REPO/account-service:$BUILD_TAG account-service
                        docker build -t $DOCKERHUB_REPO/customer-service:$BUILD_TAG customer-service
                        docker build -t $DOCKERHUB_REPO/transaction-service:$BUILD_TAG transaction-service
                        docker build -t $DOCKERHUB_REPO/api-gateway:$BUILD_TAG api-gateway
                        docker build -t $DOCKERHUB_REPO/eureka-server:$BUILD_TAG eureka-server

                        docker push $DOCKERHUB_REPO/account-service:$BUILD_TAG
                        docker push $DOCKERHUB_REPO/customer-service:$BUILD_TAG
                        docker push $DOCKERHUB_REPO/transaction-service:$BUILD_TAG
                        docker push $DOCKERHUB_REPO/api-gateway:$BUILD_TAG
                        docker push $DOCKERHUB_REPO/eureka-server:$BUILD_TAG
                    '''
                }
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker compose down'
                sh 'docker compose up -d'
            }
        }
    }

    post {
        success {
            echo 'All microservices built and deployed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
