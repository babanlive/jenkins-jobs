pipeline {
    agent any

    options {
        timestamps()                    // 📝 Добавляет время к каждому шагу
        timeout(time: 30, unit: 'MINUTES') // ⏰ Таймаут на всю сборку
    }

    environment {
        MESSAGE = "Hello from Jenkins Pipeline!"
        BUILD_DISPLAY_NAME = "${env.JOB_NAME} #${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                echo "📦 Checking out code..."
                checkout scm
                script {
                    currentBuild.displayName = "#${env.BUILD_NUMBER} - ${env.MESSAGE}"
                    currentBuild.description = "Pipeline from ${env.JOB_NAME}"
                }
            }
        }

        stage('Build') {
            steps {
                echo "🛠️ Building project..."
                sh '''
                    echo "Build started at: $(date)"
                    echo "Working directory: $(pwd)"
                    echo "Building application..."
                    sleep 2
                    echo "Build completed!"
                '''
            }
            
            post {
                success {
                    echo "✅ Build stage completed successfully!"
                }
            }
        }

        stage('Test') {
            steps {
                echo "🧪 Running tests..."
                sh '''
                    echo "Running unit tests..."
                    echo "Tests passed: 42/42"
                    echo "Code coverage: 85%"
                '''
            }
            
            post {
                success {
                    echo "✅ All tests passed!"
                }
            }
        }

        stage('Deploy') {
            steps {
                echo "🚀 Deploying application..."
                sh '''
                    echo "Preparing deployment..."
                    echo "Deploying to environment: development"
                    echo "Deployment completed successfully!"
                '''
            }
            
            post {
                success {
                    echo "✅ Deployment successful!"
                    emailext (
                        subject: "SUCCESS: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                        body: "Pipeline completed successfully!\nBuild URL: ${env.BUILD_URL}",
                        to: "admin@example.com"
                    )
                }
            }
        }
    }

    post {
        always {
            echo "🏁 Pipeline execution completed"
            cleanWs()  // 🧹 Очистка workspace после сборки
        }
        success {
            echo "🎉 Pipeline completed successfully!"
            script {
                currentBuild.result = 'SUCCESS'
            }
        }
        failure {
            echo "❌ Pipeline failed!"
            script {
                currentBuild.result = 'FAILURE'
            }
        }
        unstable {
            echo "⚠️ Pipeline marked as unstable"
        }
    }
}