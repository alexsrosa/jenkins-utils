package org.fundacred.jenkins.utils.stage


import org.fundacred.jenkins.utils.git.GitMessageCommit
import org.fundacred.jenkins.utils.git.GitTags

class GitStage implements Serializable {

    private def steps

    GitStage(steps) {
        this.steps = steps
    }

    def validationGoal() {
        new GitMessageCommit(this.steps).validateLastMessageCommit()
    }

    def newTagGoal() {
        new GitTags(this.steps).generateNewTag()
    }
}
