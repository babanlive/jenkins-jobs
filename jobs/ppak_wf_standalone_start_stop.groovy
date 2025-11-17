folder('Functions') {
    description('Utility and maintenance jobs')
}

pipelineJob('Functions/ppak-wf-standalone-start-stop') {
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url('git@github.com:babanlive/jenkins-jobs.git')
                        credentials('github_babanlive_ssh')
                    }
                    branch('*/ppak-wf-standalone-start-stop')
                }
            }
            scriptPath('Jenkinsfile')
        }
    }
    
    parameters {
        choiceParam('ACTION', ['start', 'stop'], 'Выбор действия с Wildfly: start/stop')
        stringParam('STAND', 'ppak', 'Выбор стенда (ppak)')
        choiceParam('TAGS', ['', 'saiku'], 'Выбор тэга ansible (saiku - для выбора хостов ppak-app-wf-saiku, иначе оставить пустым)')
    }
    
    logRotator {
        numToKeep(5)
    }
}