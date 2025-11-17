folder('Tests') {
    displayName('Test Jobs')
    description('Jobs for testing purposes')
}

pipelineJob("Tests/TestPipeline") {
    definition {
        cps {
            script('''
                pipeline {
                    agent any
                    stages {
                        stage('Test') {
                            steps {
                                echo 'Hello World!'
                            }
                        }
                    }
                }
            ''')
            sandbox(true)
        }
    }
    
    properties {
        disableConcurrentBuilds()
    }
}