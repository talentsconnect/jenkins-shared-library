def call(Map config = [:]) {
    
    sh "echo Version: ${config.version}"
    sh "echo Repo: ${config.nexusRepository}"
    sh "echo Path: ${config.registryPath}"
    
    script {
        env.MAJOR = sh (
                script: 'echo "${config.version}" | sed -r \'s/^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*).*/\\1/\'',
                returnStdout: true
        ).trim()
    }
    sh 'docker image push ${config.nexusRepository}/${config.registryPath}:${MAJOR}'

    script {
        env.MINOR = sh (
                script: 'echo "${config.version}" | sed -r \'s/^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*).*/\\1\\.\\2/\'',
                returnStdout: true
        ).trim()
    }
    sh 'docker image push ${config.nexusRepository}/${config.registryPath}:${MINOR}'

    script {
        env.PATCH = sh (
                script: 'echo "${config.version}" | sed -r \'s/^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*).*/\\1\\.\\2\\.\\3/\'',
                returnStdout: true
        ).trim()
    }
    sh 'docker image push ${config.nexusRepository}/${config.registryPath}:${PATCH}'

    sh 'docker image push ${config.nexusRepository}/${config.registryPath}:latest'
}
