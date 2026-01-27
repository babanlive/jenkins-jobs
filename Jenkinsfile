def update_reference_git() {
  withCredentials([sshUserPrivateKey(credentialsId: "CredID_SSH_Jenkins_Masters_to_Gitlab", keyFileVariable: 'keyfile')]) {
    sh """
       cd "${HCS_GIT_REFERENCE_ROOT}/hcs_infra_ansible.git" && git -c core.sshCommand="/bin/ssh -i ${keyfile}" fetch --all --prune
    """
  }
}

def send_job_success_status_to_telegram() {
  telegramSend(
    message: '''
      🟢 *[JENKINS - WILDFLY CONTROL]*:
      *Job:* [${JOB_NAME}]
      *Action:* ${ACTION}
      *Status:* [${BUILD_STATUS}]
    ''',
    chatId: -1003048081797
  )
}

def send_job_failure_status_to_telegram() {
  telegramSend(
    message: '''
      🔴 *[JENKINS - WILDFLY CONTROL]*:
      *Job:* [${JOB_NAME}]
      *Action:* ${ACTION}
      *Status:* [${BUILD_STATUS}]
    ''',
    chatId: -1003048081797
  )
}

def run_wildfly_playbook(action, stand_name) {
    dir("${WORKSPACE}/hcs_infra_ansible.git") {
        ansiColor('xterm') {
            def playbook
            if (action == 'start') {
                playbook = 'wildfly_standalone_start.yml'
            } else {
                playbook = 'wildfly_standalone_stop.yml'
            }
            def extraVars = [:]
            if (params.TAGS) {
                extraVars.tags = params.TAGS
            }
            ansiblePlaybook(
                credentialsId: 'CredID_SSH_Jenkins_as_exec_into_hosts',
                colorized: true,
                inventory: "inventories/voshod/${stand_name}",
                playbook: "playbooks/${playbook}",
                extraVars: extraVars
            )
        }
    }
}

pipeline {
    agent {
        label 'master'
    }
    options {
        timestamps()
    }
    parameters {
        choice(name: 'ACTION', choices: ['start', 'stop'])
        string(name: 'STAND', defaultValue: 'ppak')
        string(name: 'TAGS', defaultValue: '')
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
                        branches: [[name: "master"]],
                        doGenerateSubmoduleConfigurations: false,
                        extensions: [
                            [$class: 'CloneOption', reference: "${HCS_GIT_REFERENCE_ROOT}/hcs_infra_ansible.git", shallow: true, depth: "1"],
                            [$class: 'RelativeTargetDirectory', relativeTargetDir: "hcs_infra_ansible.git"],
                        ],
                        gitTool: 'Default',
                        userRemoteConfigs: [[url: 'git@git.oisrf.ru:hcs/hcs_infra_ansible.git', credentialsId: 'CredID_SSH_Jenkins_Masters_to_Gitlab']]
                    ])
                }
            }
        }
        stage('Wildfly Control') {
            steps {
                script {
                    run_wildfly_playbook(params.ACTION, params.STAND)
                }
            }
        }
    }
    post {
        success {
            send_job_success_status_to_telegram()
        }
        failure {
            send_job_failure_status_to_telegram()
        }
    }
}
