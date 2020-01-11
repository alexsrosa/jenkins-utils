package org.fundacred.jenkins.utils.stage

import org.fundacred.jenkins.utils.general.PropertiesForStage

class DeployStage implements Serializable {

    private def steps
    private PropertiesForStage propertiesForStage

    DeployStage(steps) {
        this.steps = steps
        this.propertiesForStage = new PropertiesForStage(steps)
    }

    def deployGoal() {

        /**
         * Here you need to enter correct Jenkins variables.
         */
        switch (this.steps.BRANCH_NAME) {
            case PropertiesForStage.MASTER:
                this.deployEnvironment(this.steps.docker_production) // Variable from Jenkins
                break
            case PropertiesForStage.HOMOLOGATION:
                this.deployEnvironment(this.steps.docker_homologation) // Variable from Jenkins
                break
            case PropertiesForStage.TEST:
                this.deployEnvironment(this.steps.docker_teste) // Variable from Jenkins
                break
        }
    }

    private def deployEnvironment(String environment) {

        this.steps.sh("ssh root@${environment} " +
                "docker service update " +
                "--image ${this.propertiesForStage.getRegistryName()}:${this.propertiesForStage.getNextVersion()} " +
                "--force ${this.steps.dockerStackName}_${this.steps.projectName}")
    }
}
