package org.fundacred.jenkins.utils.build

class BuildUtils {

    private def steps

    BuildUtils(steps) {
        this.steps = steps
    }

    String getTypeOfBuild(){
        def typeOfBuild = ""

        if (this.steps.BRANCH_NAME == 'homologation') {
            typeOfBuild = "-RC"
        } else if (this.steps.BRANCH_NAME == 'test') {
            typeOfBuild = "-PR"
        }

        return typeOfBuild
    }
}
