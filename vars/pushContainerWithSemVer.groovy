def call(Map config = [:]) {

    sh "docker push ${config.nexusRepository}/${config.registryPath}:${config.version}"
    sh "docker push ${config.nexusRepository}/${config.registryPath}:${config.majorTag}"
    sh "docker push ${config.nexusRepository}/${config.registryPath}:${config.majorMinorTag}"
    sh "docker push ${config.nexusRepository}/${config.registryPath}:latest"
}
