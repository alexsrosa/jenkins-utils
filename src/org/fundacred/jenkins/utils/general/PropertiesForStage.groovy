package org.fundacred.jenkins.utils.general


import org.fundacred.jenkins.utils.git.GitTags

class PropertiesForStage implements Serializable {

    /**
     * Here you need to input correct SONAR_URL.
     */
    public static final String SONAR_URL = 'http://SONAR_URL:9100'
    public static final String MASTER = "master"
    public static final String HOMOLOGATION = 'homologation'
    public static final String TEST = 'test'

    private static final String RC = "-RC"
    private static final String PR = "-PR"

    /**
     * Here you need to input correct REGISTRY from docker.
     */
    private static final String REGISTRY = 'REGISTRY_NAME/'

    private def steps

    PropertiesForStage(steps) {
        this.steps = steps
    }

    /**
     * Method that seeks the next version.
     * @return new version
     */
    String getNextVersion() {

        String typeOfBuild = getTypeOfBuild()

        if (MASTER == this.steps.BRANCH_NAME) {
            String newTag = new GitTags(steps).getNewTag()
            return "${newTag}.${this.steps.BUILD_NUMBER}${typeOfBuild}"
        } else {
            String currentVersion = new GitTags(steps).getMostRecentTag()
            return "${currentVersion}.${this.steps.BUILD_NUMBER}${typeOfBuild}"
        }
    }

    /**
     * Method that returns the build type.
     * @return build type
     */
    String getTypeOfBuild() {
        def typeOfBuild = ""

        if (this.steps.BRANCH_NAME == HOMOLOGATION) {
            typeOfBuild = RC
        } else if (this.steps.BRANCH_NAME == TEST) {
            typeOfBuild = PR
        }

        return typeOfBuild
    }

    String getRegistryName() {
        return REGISTRY + this.steps.projectName
    }
}
