pipelineJob('TestPipeline') {
    description('🚀 Пример простого pipeline job, созданного через JobDSL из Git.')

    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url('git@github.com:babanlive/jenkins-jobs.git')  // ⬅️ Прямой URL
                    }
                    branch('main')  // ⬅️ Прямое указание ветки
                }
            }
            scriptPath('Jenkinsfile') // путь до Jenkinsfile в репозитории
        }
    }

    triggers {
        scm('H/5 * * * *') // запуск каждые 5 минут
    }

    logRotator {
        numToKeep(10)
    }
    
    properties {
        disableConcurrentBuilds()
    }
}