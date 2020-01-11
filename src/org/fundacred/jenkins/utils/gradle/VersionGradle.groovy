package org.fundacred.jenkins.utils.gradle


import org.fundacred.jenkins.utils.general.Commons
import org.fundacred.jenkins.utils.git.GitTags

class VersionGradle implements Serializable {

    private def steps
    private Commons commons

    VersionGradle(steps) {
        this.steps = steps
        this.commons = new Commons(steps)
    }

    /**
     * Apenas retorna a ultima versão do git.
     * @return ultima versão
     */
    String getMostVersionInGit() {
        GitTags tags = new GitTags(steps)
        String mostRecentTag = tags.getMostRecentTag()
        return mostRecentTag
    }
}
