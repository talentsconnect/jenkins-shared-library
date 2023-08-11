def call(Map config = [:]) {

    script {
        sh '''
            echo waitForHealthyContainer invoked
            echo \$config
            echo \$config.refId
            REXP=jobshop_test_stack_$config.refId_$config.serviceName
            echo waitForHealthyContainer expression = $REXP
            containerId = docker ps | awk "/$REXP/"'{print $1}'
            echo waitForHealthyContainer containerId = $containerId
            containerState = docker inspect -f {{.State.Health.Status}} $containerId
            i=0
            while [$x -lt $config.retries] || [ $containerState -ne "healthy" ]; 
            do     
                ((i++))
                sleep $config.timeout; 
            done
        '''
    }

    return sh (
            script: "$containerState",
            returnStdout: true
    ).trim()
}