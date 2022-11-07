def call(Map config = [:]) {
    
    sh "echo Version: ${config.version}"
    sh "echo MajorTag: ${config.majorTag}"
    sh "echo MajorMinorTag: ${config.majorMinorTag}"
    sh "echo Repo: ${config.nexusRepository}"
    sh "echo Path: ${config.registryPath}"

    sh "docker build -t ${config.nexusRepository}/${config.registryPath}:${config.majorTag} ."
    sh "docker build -t ${config.nexusRepository}/${config.registryPath}:${config.majorMinorTag} ."
    sh "docker build -t ${config.nexusRepository}/${config.registryPath}:${config.version} ."
    sh "docker build -t ${config.nexusRepository}/${config.registryPath}:latest ."
}
