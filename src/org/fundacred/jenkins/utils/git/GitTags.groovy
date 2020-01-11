package org.fundacred.jenkins.utils.git

import org.fundacred.jenkins.utils.general.Commons
import org.fundacred.jenkins.utils.enuns.VersionIncremetEnum

class GitTags implements Serializable {

    /**
     * Here you need to input correct CREDENTIALS_ID from Jenkins.
     */
    private def CREDENTIALS_ID = "00844153-EXAMPLE-49ed-EXAMPLE-b8120ccb8a68"
    private def MAJOR_PATTERN = "RELEASE-MAJOR-VERSION"
    private def MINOR_PATTERN = "Merged in release"
    private def PATCH_PATTERN = "Merged in hotfix"

    private String TAG_INITIAL = "0.0.0"

    private def steps
    private def versionIncrement
    private def tags
    private String newTag
    private String mostRecentTag
    private Commons commons

    GitTags(steps) {
        this.steps = steps
        this.commons = new Commons(steps)
    }

    /**
     * Complete method that identifies the last commit, creates the tag and pushes it to git.
     */
    def generateNewTag() {
        identifyTypeCommit()
        defineNewTag()
        createGitTag()
    }

    /**
     * Additional method for passing a fixed tag in git.
     *
     * @param newTagArgument new tag
     */
    def generateNewTag(String newTagArgument) {
        if (newTagArgument == null) {
            return this.generateNewTag()
        }

        this.setNewTag(newTagArgument)
        this.createGitTag()
    }

    /**
     * Method that only returns the new tag based on the last commit of the repository.
     * @return new tag
     */
    String getNewTag() {
        if (this.newTag == null) {
            this.identifyTypeCommit()
            this.defineNewTag()
        }

        return this.newTag
    }

    def setNewTag(newTag) {
        this.newTag = newTag
    }

    String getMostRecentTag() {
        if (this.mostRecentTag == null) {
            this.loadMostRecentTag()
        }
        return this.mostRecentTag
    }

    void loadMostRecentTag() {
        this.loadAllTags()
        if (this.tags != null && this.tags.size() > 0) {
            this.tags.sort { x, y -> compareTags(x, y) }

            this.commons.info("TAGS: ${this.tags}")

            this.setMostRecentTag(this.tags.last())
        } else {
            this.setMostRecentTag(this.TAG_INITIAL)
        }
    }

    void setMostRecentTag(String mostRecentTag) {
        this.mostRecentTag = mostRecentTag
    }

    private def identifyTypeCommit() {

        def gitMessageCommit = new GitMessageCommit(this.steps)
        String lastCommitMessage = gitMessageCommit.getLastMessageCommit()

        gitMessageCommit.validateLastMessageCommit()

        if (lastCommitMessage.contains(MAJOR_PATTERN)) {
            versionIncrement = VersionIncremetEnum.MAJOR
        } else if (lastCommitMessage.contains(MINOR_PATTERN)) {
            versionIncrement = VersionIncremetEnum.MINOR
        } else if (lastCommitMessage.contains(PATCH_PATTERN)) {
            versionIncrement = VersionIncremetEnum.PATCH
        } else {
            commons.error(
                    "No versioning type identified in commit: ${lastCommitMessage}",
                    true)
        }
    }

    private def loadAllTags() {
        def gitTagOutput = this.steps.sh(script: "git tag", returnStdout: true)
        this.tags = gitTagOutput.split("\n").findAll { it =~ /^\d+\.\d+\.\d+$/ }
    }

    private def defineNewTag() {
        def mostRecentTagParts = getMostRecentTag().tokenize('.')

        this.commons.info("Current Repository Tags: {$tags}, Increment Type: ${versionIncrement} )")

        def newTagMajor = mostRecentTagParts[0].toInteger()
        def newTagMinor = mostRecentTagParts[1].toInteger()
        def newTagPatch = mostRecentTagParts[2].toInteger()

        switch (this.versionIncrement) {
            case VersionIncremetEnum.MAJOR:
                newTagMajor++
                newTagMinor = 0
                newTagPatch = 0
                break
            case VersionIncremetEnum.MINOR:
                newTagMinor++
                newTagPatch = 0
                break
            case VersionIncremetEnum.PATCH:
                newTagPatch++
                break
        }

        setNewTag("${newTagMajor}.${newTagMinor}.${newTagPatch}")
    }

    private def compareTags(String tag1, String tag2) {
        def tag1Split = tag1.tokenize('.')
        def tag2Split = tag2.tokenize('.')

        for (def index in (0..2)) {
            def result = tag1Split[index] <=> tag2Split[index]
            if (result != 0) {
                return result
            }
        }

        return 0
    }

    private def createGitTag() {
        def tagStatus = this.steps.sh(script: "git tag ${this.newTag}", returnStatus: true)

        if (tagStatus != 0) {
            this.commons.error("Unable to generate tag: '${this.newTag}'", true)
        }

        try {
            this.steps.withCredentials(
                    [[$class          : 'UsernamePasswordMultiBinding', credentialsId: CREDENTIALS_ID,
                      usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD']]) {

                this.steps.sh("git config credential.username ${this.steps.env.GIT_USERNAME}")
                this.steps.sh("git config credential.helper '!echo password=\$GIT_PASSWORD; echo'")
                this.steps.sh("GIT_ASKPASS=true git push origin ${this.newTag}")
                this.steps.sh("git config --unset credential.username")
                this.steps.sh("git config --unset credential.helper")
            }
        } catch (Exception ex) {
            this.commons.error(ex as String, true)
        }
    }
}
