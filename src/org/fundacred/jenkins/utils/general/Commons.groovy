package org.fundacred.jenkins.utils.general

class Commons {

    private def steps

    Commons(steps) {
        this.steps = steps
    }

    def debug(String str) {
        steps.echo "[DEBUG] ${str}"
    }

    def info(String str) {
        steps.echo "[INFO] ${str}"
    }

    def error(String str, Boolean isException) {
        if (isException) {
            throw new Exception(str)
        }
        steps.echo "[ERROR] ${str}"
    }
}
