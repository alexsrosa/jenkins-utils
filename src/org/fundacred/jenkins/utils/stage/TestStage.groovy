package org.fundacred.jenkins.utils.stage

class TestStage implements Serializable {

    private def steps

    TestStage(steps) {
        this.steps = steps
    }

    /**
     * Method that performs the Test Goal by the gradle.
     */
    def testGoal() {
        this.steps.sh("sh gradlew test")
    }
}
