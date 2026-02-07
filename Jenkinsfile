def update_reference_git() {
  withCredentials([sshUserPrivateKey(credentialsId: "CredID_SSH_Jenkins_Masters_to_Gitlab", keyFileVariable: 'keyfile')]) {
    sh """
       cd "${HCS_GIT_REFERENCE_ROOT}/ipr.git" && git -c core.sshCommand="/bin/ssh -i ${keyfile}" fetch --all --prune
    """
  }
}

def run_wildfly_playbook(action_param, stand_param, tag_param, dry_run_param) {
    dir("${WORKSPACE}/ipr.git") {
        ansiColor('xterm') {
            def playbook = (action_param == 'start') ? 'wildfly_standalone_start.yml' : 'wildfly_standalone_stop.yml'
            def dry_run = dry_run_param ? "--check" : ""
            ansiblePlaybook(
                credentialsId: 'CredID_SSH_Jenkins_as_exec_into_hosts',
                colorized: true,
                inventory: "inventories/${stand_param}",
                playbook: "playbooks/${playbook}",
                tags: tag_param ?: "",
                checkMode: dry_run_param
            )
        }
    }
}

pipeline {
    agent {
        label 'slave'
    }
    options {
        timestamps()
    }
    parameters {
        booleanParam(name: 'DRY_RUN', defaultValue: true, description: 'Если отмечено - Ansible запустится с флагом --check (без изменений на сервере)')
        choice(name: 'ACTION', choices: ['start', 'stop'], description: 'Выбор действия с Wildfly: start/stop')
        string(name: 'STAND', defaultValue: 'local.ini', description: 'Выбор стенда')
        choice(name: 'TAGS', choices: ['', 'saiku'], description: 'Оставить пустым - для выбора всех ячеек;  SAIKU - выбрать только ячейку saiku')
    }
    environment {
        HCS_GIT_REFERENCE_ROOT = '/u01/jenkins_cache/git'
    }
    stages {
        stage("Checkout GIT REFERENCE") {
            steps {
                script {
                    update_reference_git()
                }
            }
        }
        stage('Git checkout hcs_infra_ansible') {
            steps {
                dir("${WORKSPACE}") {
                    checkout([$class: 'GitSCM',
                        branches: [[name: "main"]],
                        doGenerateSubmoduleConfigurations: false,
                        extensions: [
                            [$class: 'CloneOption', reference: "${HCS_GIT_REFERENCE_ROOT}/ipr.git", shallow: true, depth: "1"],
                            [$class: 'RelativeTargetDirectory', relativeTargetDir: "ipr.git"],
                        ],
                        gitTool: 'Default',
                        userRemoteConfigs: [[url: 'git@gitlab.on1x-pwnz.ru:babanlive/ipr.git', credentialsId: 'CredID_SSH_Jenkins_Masters_to_Gitlab']]
                    ])
                }
            }
        }

        stage("Action Wildfly") {
            steps {
                echo "Executing ${params.ACTION} on ${params.STAND}"
                script {
                    run_wildfly_playbook(params.ACTION, params.STAND, params.TAGS, params.DRY_RUN)
                }
            }
        }
    }
}
