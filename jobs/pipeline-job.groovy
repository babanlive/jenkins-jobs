pipelineJob('TestPipeline') {
    description('🚀 Пример простого pipeline job, созданного через JobDSL из Git.')

    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url('{{ jenkins_jobs_repo_url }}')  // URL берётся из твоего defaults/main.yml
                    }
                    branch('{{ jenkins_jobs_repo_branch }}')
                }
            }
            scriptPath('Jenkinsfile') // путь до Jenkinsfile в репозитории
        }
    }

    triggers {
        scm('H/5 * * * *') // запуск каждые 5 минут, можно заменить
    }

    logRotator {
        numToKeep(10)
    }
}
