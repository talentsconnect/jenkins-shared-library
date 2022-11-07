def call(Map config = [:]) {
    
    sh "echo Version: ${config.version}"
    sh "echo MajorTag: ${config.majorTag}"
    sh "echo MajorMinorTag: ${config.majorMinorTag}"
    sh "echo Repo: ${config.nexusRepository}"
    sh "echo Path: ${config.registryPath}"

    sh '''
    docker build \\
        --build-arg BUILD_DATE=$(date -u +\'%Y-%m-%dT%H:%M:%SZ\') \\
        --build-arg VCS_REF=$(git rev-parse --abbrev-ref HEAD)-$(git rev-parse --short=8 HEAD) \\
        --build-arg VERSION=${config.majorTag} \\
        --tag ${config.nexusRepository}/${config.registryPath}:${config.majorTag} .             
    '''

    sh '''
    docker build \\
        --build-arg BUILD_DATE=$(date -u +\'%Y-%m-%dT%H:%M:%SZ\') \\
        --build-arg VCS_REF=$(git rev-parse --abbrev-ref HEAD)-$(git rev-parse --short=8 HEAD) \\
        --build-arg VERSION=${config.majorMinorTag} \\
        --tag ${config.nexusRepository}/${config.registryPath}:${config.majorMinorTag} .             
    '''

    sh '''
    docker build \\
        --build-arg BUILD_DATE=$(date -u +\'%Y-%m-%dT%H:%M:%SZ\') \\
        --build-arg VCS_REF=$(git rev-parse --abbrev-ref HEAD)-$(git rev-parse --short=8 HEAD) \\
        --build-arg VERSION=${config.version} \\
        --tag ${config.nexusRepository}/${config.registryPath}:${config.version} .             
    '''

    sh '''
    docker build \\
        --build-arg BUILD_DATE=$(date -u +\'%Y-%m-%dT%H:%M:%SZ\') \\
        --build-arg VCS_REF=$(git rev-parse --abbrev-ref HEAD)-$(git rev-parse --short=8 HEAD) \\
        --build-arg VERSION=latest \\
        --tag ${config.nexusRepository}/${config.registryPath}:latest .             
    '''

}
