package org.fundacred.jenkins.utils.docker

class DeployDocker implements Serializable {

    private def steps

    DeployDocker(steps) {
        this.steps = steps
    }

    def deployInDocker(String hostDocker, String dockerStackName, String dockerImageName){
        this.steps.sh("ssh root@${hostDocker} docker service update --force ${dockerStackName}_${dockerImageName}")
    }

    def deployInDocker(String hostDocker, String dockerStackName, String dockerImageName, String imageName){
        this.steps.sh("ssh root@${hostDocker} docker service update --image ${imageName} --force ${dockerStackName}_${dockerImageName}")
    }
}
