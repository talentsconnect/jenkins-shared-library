def call(Map config = [:]) {

    script {
        env.CURRENT_TIME = sh(returnStdout: true, script: 'date -u +%Y-%m-%dT%H:%M:%SZ').trim()
        env.VCS_REF = sh(returnStdout: true, script: 'echo $(git rev-parse --abbrev-ref HEAD)-$(git rev-parse --short=8 HEAD)').trim()
    }

    sh "docker build " +
            "--build-arg BUILD_DATE=${CURRENT_TIME} " +
            "--build-arg VCS_REF=${VCS_REF} " +
            "--build-arg VERSION=${config.majorTag} " +
            "--tag ${config.nexusRepository}/${config.registryPath}:${config.majorTag} ."

    sh "docker build " +
            "--build-arg BUILD_DATE=${CURRENT_TIME} " +
            "--build-arg VCS_REF=${VCS_REF} " +
            "--build-arg VERSION=${config.majorMinorTag} " +
            "--tag ${config.nexusRepository}/${config.registryPath}:${config.majorMinorTag} ."

    sh "docker build " +
            "--build-arg BUILD_DATE=${CURRENT_TIME} " +
            "--build-arg VCS_REF=${VCS_REF} " +
            "--build-arg VERSION=${config.version} " +
            "--tag ${config.nexusRepository}/${config.registryPath}:${config.version} ."

    sh "docker build " +
            "--build-arg BUILD_DATE=${CURRENT_TIME} " +
            "--build-arg VCS_REF=${VCS_REF} " +
            "--build-arg VERSION=latest " +
            "--tag ${config.nexusRepository}/${config.registryPath}:latest ."
}
