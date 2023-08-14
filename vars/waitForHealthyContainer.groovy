def call(Map config = [:]) {

    script {
        env.refId = sh(returnStdout: true, script: "echo $config.refId").trim()
        env.serviceName = sh(returnStdout: true, script: "echo $config.serviceName").trim()

        env.container_id = sh(
                returnStdout: true,
                script: "docker ps|awk '/jobshop_test_stack_${refId}_${serviceName}/{print \$1}'"
        ).trim()

        def retries = sh(script: "echo ${retries}")
        def timeout = sh(script: "echo ${timeout}")

        for (int i = 0; i < retries.toInteger(); i++) {
            sh(script: "echo loop started")
            healthStatus = sh(
                    returnStdout: true,
                    script: "docker inspect --format='{{.State.Health.Status}}' ${container_id}"
            ).trim()

            sh(script: "echo ${healthStatus}")

            if (healthStatus == "healthy") {
                break
            }

            sleep timeout.toInteger()
        }
    }

    return sh (
            script: "$healthStatus",
            returnStdout: true
    ).trim()
}