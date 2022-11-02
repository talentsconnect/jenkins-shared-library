def call(Map config = [:]) {
    sh "echo Printing: ${config.parameter}"
}