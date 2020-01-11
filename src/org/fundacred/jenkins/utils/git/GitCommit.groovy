package org.fundacred.jenkins.utils.git

import org.fundacred.jenkins.utils.general.Commons

class GitCommit implements Serializable {

    /**
     * Here you need to input correct CREDENTIALS_ID from Jenkins.
     */
    private def CREDENTIALS_ID = "00844153-EXAMPLE-49ed-8655-EXAMPLE"
    private def steps

    GitCommit(steps) {
        this.steps = steps
    }

    def processCommit(String message) {

        try {
            this.steps.withCredentials(
                    [[$class          : 'UsernamePasswordMultiBinding', credentialsId: CREDENTIALS_ID,
                      usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD']]) {

                this.config()
                this.addAll()
                this.commit(message)
                this.push()
                this.resetConfig()
            }
        } catch (Exception ex) {
            new Commons(this.steps).error(ex as String, true)
        }
    }

    private def config() {
        this.steps.sh("git config credential.username ${this.steps.env.GIT_USERNAME}")
        this.steps.sh("git config credential.helper '!echo password=\$GIT_PASSWORD; echo'")
        this.steps.sh("git config user.name '${this.steps.env.GIT_USERNAME}'")
        this.steps.sh("git config user.email '${this.steps.env.GIT_USERNAME}@mail.com'")
    }

    private def addAll() {
        this.steps.sh("git add . ")
    }

    private def commit(String message) {
        this.steps.sh("git commit -m '${message}'")
    }

    private def push() {
        this.steps.sh("GIT_ASKPASS=true git fetch")
        this.steps.sh("GIT_ASKPASS=true git push origin ${this.steps.BRANCH_NAME}")
    }

    private def resetConfig() {
        this.steps.sh("git config --unset credential.username")
        this.steps.sh("git config --unset credential.helper")
    }
}
