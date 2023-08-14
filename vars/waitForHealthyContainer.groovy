def call(Map config = [:]) {

    script {
        env.refId = config.refId
        env.serviceName = config.serviceName

        sleep config.timeout.toInteger()

        env.container_id = sh(
                returnStdout: true,
                script: "docker ps|awk '/jobshop_test_stack_${refId}_${serviceName}/{print \$1}'"
        ).trim()

        echo "containerId is $env.container_id"

        for (int i = 0; i < config.retries.toInteger(); i++) {
            healthStatus = sh(
                    returnStdout: true,
                    script: "docker inspect --format='{{.State.Health.Status}}' ${container_id}"
            ).trim()

            if (healthStatus == "healthy") {
                break
            }

            sleep config.timeout.toInteger()
        }
    }

    return healthStatus
}