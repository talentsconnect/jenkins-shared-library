def call(Map config = [:]) {
     return sh (
            script: "echo ${config.version} | sed -r 's/^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*).*/\\1/'",
            returnStdout: true
    ).trim()
}