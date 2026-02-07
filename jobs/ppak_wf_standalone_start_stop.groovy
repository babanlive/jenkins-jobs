folder('Functions') {
    description('Utility and maintenance jobs')
}

pipelineJob('Functions/ppak-wf-standalone-start-stop') {
    definition {
        cpsScm {
            scm {
                git {
                    browser {}
                    remote {
                        url('git@gitlab.on1x-pwnz.ru:babanlive/ipr_jenkins.git')
                        credentials('CredID_SSH_Jenkins_Masters_to_Gitlab')
                    }
                    extensions {
                        cloneOptions {
                            reference('/u01/jenkins_cache/git/hcs_jenkins.git')
                            shallow(false)
                            noTags(false)
                            timeout(10)
                        }
                    }
                    branch('*/ppak-wf-standalone-start-stop')
                }
            }
            lightweight()
        }
    }
    parameters {
        booleanParam('DRY_RUN', true, 'Если отмечено - Ansible запустится с флагом --check (без изменений на сервере)')
        choiceParam('ACTION', ['start', 'stop'], 'Выбор действия с Wildfly: start/stop')
        stringParam('STAND', 'ppak', 'Выбор стенда')
        choiceParam('TAGS', ['', 'saiku'], 'Оставить пустым - для выбора всех ячеек; SAIKU - выбрать только ячейку saiku')
    }
    logRotator {
        numToKeep(5)
    }
}
