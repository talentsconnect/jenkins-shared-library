def call(Map config = [:]) {

    script {
        sh '''
            echo here
            REXP=jobshop_test_stack_${config.refId}_${config.serviceName}
            containerId = docker ps | awk "/$REF/"'{print $1}'
            containerState = docker inspect -f {{.State.Health.Status}} $containerId
            i=0
            while [$x -lt ${config.retries}] || [ $containerState -ne "healthy" ]; 
            do     
                ((i++))
                sleep ${config.timeout}; 
            done
        '''
    }

    return sh (
            script: "$containerState",
            returnStdout: true
    ).trim()
}