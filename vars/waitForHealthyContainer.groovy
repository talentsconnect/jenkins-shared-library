def call(Map config = [:]) {

    script {
        env.refId = sh(returnStdout: true, script: "echo $config.refId").trim()
        env.serviceName = sh(returnStdout: true, script: "echo $config.serviceName").trim()
        env.retries = sh(returnStdout: true, script: "echo $config.retries").trim()
        env.timeout = sh(returnStdout: true, script: "echo $config.timeout").trim()


        env.container_id = sh(
                returnStdout: true,
                script: "docker ps|awk '/jobshop_test_stack_${refId}_${serviceName}/{print \$1}'"
        ).trim()

        sh(script: "echo ${container_id}")

        for (int i = 0; i < ${retries}.toInteger(); i++) {
            healthStatus = sh(
                    returnStdout: true,
                    script: "docker inspect --format='{{.State.Health.Status}}' ${container_id}"
            ).trim()

            if (healthStatus == "healthy") {
                break
            }

            sleep ${timeout}.toInteger()
        }
    }

    return sh (
            script: "$healthStatus",
            returnStdout: true
    ).trim()
}