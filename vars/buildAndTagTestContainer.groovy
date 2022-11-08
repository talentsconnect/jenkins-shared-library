def call(Map config = [:]) {

    script {
        env.CURRENT_TIME = sh(returnStdout: true, script: 'date -u +%Y-%m-%dT%H:%M:%SZ').trim()
        env.VCS_REF = sh(returnStdout: true, script: 'echo $(git rev-parse --abbrev-ref HEAD)-$(git rev-parse --short=8 HEAD)').trim()
    }

    sh "docker build " +
            "--build-arg BUILD_DATE=${CURRENT_TIME} " +
            "--build-arg VCS_REF=${VCS_REF} " +
            "--build-arg VERSION=${config.version} " +
            "--tag ${config.nexusRepository}/${config.registryPath}:${config.version} ."
}
