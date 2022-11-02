def call(Map config = [:]) {
    
    sh "echo Version: ${config.version}"
    sh "echo MajorTag: ${config.majorTag}"
    sh "echo MajorMinorTag: ${config.majorMinorTag}"
    sh "echo Repo: ${config.nexusRepository}"
    sh "echo Path: ${config.registryPath}"

    sh "docker build -t ${config.nexusRepository}/${config.registryPath}:${config.version} " +
            "${config.nexusRepository}/${config.registryPath}:${config.majorTag} " +
            "${config.nexusRepository}/${config.registryPath}:${config.majorMinorTag} " +
            "${config.nexusRepository}/${config.registryPath}:latest ."
}
