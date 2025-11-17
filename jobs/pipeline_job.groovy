pipelineJob('Tests/TestPipeline') {
    description('Pipeline job, созданный через JobDSL из GitHub репозитория.')

    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url('git@github.com:babanlive/jenkins-jobs.git')
                    }
                    branch('main')
                }
            }
            scriptPath('Jenkinsfile')
        }
    }

    triggers {
        scm('H/5 * * * *')
    }

    logRotator {
        numToKeep(10)
    }
    
    properties {
        disableConcurrentBuilds()
    }
}