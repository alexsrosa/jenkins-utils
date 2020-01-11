package org.fundacred.jenkins.utils.git

import org.fundacred.jenkins.utils.general.Commons

class GitMessageCommit implements Serializable {

    private def PULL_REQUEST_PATTERN = "pull request"
    private def steps
    private String lastMessageCommit

    GitMessageCommit(steps) {
        this.steps = steps
    }

    def validateLastMessageCommit() {

        if (lastMessageCommit == null) {
            lastMessageCommit = getLastMessageCommit()
        }

        new Commons(steps).info("Último mensagem de commit: ${lastMessageCommit}")

        if (!lastMessageCommit.contains(PULL_REQUEST_PATTERN)) {
            new Commons(steps).error(
                    "Commit não tem origem de um PULL REQUEST: ${lastMessageCommit}",
                    true)
        }
    }

    String getLastMessageCommit() {
        lastMessageCommit = steps.sh returnStdout: true, script: 'git log --pretty=format:"%s"  -1'
        return lastMessageCommit
    }
}
