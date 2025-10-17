pipeline {
    agent any

    environment {
        MESSAGE = "Hello from Jenkins Pipeline!"
    }

    stages {
        stage('Checkout') {
            steps {
                echo "📦 Checking out code..."
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo "🛠️ Building project..."
                sh 'echo "Building..."'
            }
        }

        stage('Test') {
            steps {
                echo "🧪 Running tests..."
                sh 'echo "Tests passed!"'
            }
        }

        stage('Deploy') {
            steps {
                echo "🚀 Deploying application..."
                sh 'echo "Deployed successfully!"'
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline completed successfully!"
        }
        failure {
            echo "❌ Pipeline failed!"
        }
    }
}
