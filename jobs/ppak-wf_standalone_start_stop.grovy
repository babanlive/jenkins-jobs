pipelineJob('Functions/ppak-wf-standalone-start-stop') {
    definition {
        cpsScm {
            scm {
                git {
                browser {}
                remote {
                    url('git@github.com:babanlive/jenkins-jobs.git')
                }
                branch('*/ppak-wf-standalone-start-stop')
                }
            }
            lightweight()
        }
    }
    parameters {
        choiceParam('ACTION', ['start', 'stop'], 'Выбор действия с Wildfly: start/stop')
        stringParam('STAND', 'ppak', 'Выбор стенда (ppak)')
        stringParam('TAGS', 'saiku', 'Выбор тэга ansible (saiku - для выбора хостов ppak-app-wf-saiku, иначе оставить пустым)')
    }
}