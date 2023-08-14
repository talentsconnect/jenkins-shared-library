def call(Map config = [:]) {

    script {
        env.refId = config.refId
        env.serviceName = config.serviceName
        env.retries = config.retries
        env.timeout = config.timeout

        env.container_id = sh(
                returnStdout: true,
                script: "docker ps|awk '/jobshop_test_stack_${refId}_${serviceName}/{print \$1}'"
        ).trim()

        for (int i = 0; i < env.retries.toInteger(); i++) {
            healthStatus = sh(
                    returnStdout: true,
                    script: "docker inspect --format='{{.State.Health.Status}}' ${container_id}"
            ).trim()

            if (healthStatus == "healthy") {
                break
            }

            sleep env.timeout.toInteger()
        }
    }

    return healthStatus
}