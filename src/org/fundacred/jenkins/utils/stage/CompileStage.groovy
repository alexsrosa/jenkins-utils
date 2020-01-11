package org.fundacred.jenkins.utils.stage

import org.fundacred.jenkins.utils.general.PropertiesForStage

class CompileStage implements Serializable {

    private def steps
    private PropertiesForStage propertiesForStage

    CompileStage(steps) {
        this.steps = steps
        this.propertiesForStage = new PropertiesForStage(steps)
    }

    /**
     * Method that performs Compile Goal by gradle.
     */
    def compileGoal() {
        this.steps.sh("sh gradlew clean incrementVersion -PnewVersion=${this.propertiesForStage.getNextVersion()} compileJava assemble")
    }
}
