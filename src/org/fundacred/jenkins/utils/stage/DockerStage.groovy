package org.fundacred.jenkins.utils.stage

import org.fundacred.jenkins.utils.general.Commons
import org.fundacred.jenkins.utils.general.PropertiesForStage

class DockerStage implements Serializable {

    private def steps
    private PropertiesForStage propertiesForStage
    private Commons commons

    DockerStage(steps) {
        this.steps = steps
        this.propertiesForStage = new PropertiesForStage(steps)
        this.commons = new Commons(steps)
    }

    def buildGoal() {
        return this.steps.docker.build(this.propertiesForStage.getRegistryName() + ":" + this.propertiesForStage.getNextVersion())
    }

    def pushGoal() {
        this.steps.dockerImage.push()
        this.steps.dockerImage.push("latest${this.propertiesForStage.getTypeOfBuild()}")
    }

    def cleanGoal() {
        try {
            this.steps.sh"docker rmi \$(docker images --format \"{{.Repository}}:{{.Tag}}\" | grep \"${this.propertiesForStage.getRegistryName()}\")"
        } catch (Exception ex) {
            this.commons.error(ex as String, false)
        }

        this.steps.deleteDir()
    }
}
