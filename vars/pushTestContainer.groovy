def call(Map config = [:]) {
    sh "docker push ${config.nexusRepository}/${config.registryPath}:${config.version}"
}
