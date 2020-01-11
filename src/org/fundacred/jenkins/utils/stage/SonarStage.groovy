package org.fundacred.jenkins.utils.stage

import org.fundacred.jenkins.utils.general.PropertiesForStage

class SonarStage implements Serializable {

    private def steps

    SonarStage(steps) {
        this.steps = steps
    }

    /**
     * Method that performs the sonar Goal through the gradle, sending statistics to the sonar.
     */
    def sonarGoal() {
        this.steps.sh("sh gradlew sonarqube -Dsonar.projectKey=${this.steps.projectName} " +
                "-Dsonar.host.url=${PropertiesForStage.SONAR_URL} -Dsonar.login=${this.steps.sonarToken}")
    }
}
