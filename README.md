# Jenkins utils for use as a jenkins library 

## Documentation  ---------------------------------------------
https://jenkins.io/doc/book/pipeline/shared-libraries/

Basic structure of `libraries` for use in Jenkins.

(root)
+- src                     # Groovy source files <br>
|   +- org<br>
|       +- foo<br>
|           +- Bar.groovy  # for org.foo.Bar class<br>
+- vars<br>
|   +- foo.groovy          # for global 'foo' variable<br>
|   +- foo.txt             # help for 'foo' variable<br>
+- resources               # resource files (external libraries only)<br>
|   +- org<br>
|       +- foo<br>
|           +- bar.json    # static helper data for org.foo.Bar<br>

## Use of libraries

- <b>Step 1:</b> In class ``GitTags``, in constant ``CREDENTIALS_ID`` credential id must be entered existing at Jenkins.

- <b>Step 2:</b> In jenkins you need to configure the existing link below:
https://jenkins.io/doc/book/pipeline/shared-libraries/#global-shared-libraries

- <b>Step 3:</b> Inside `Jenkinsfile`, include the lib import statement in the first line of file: 

```
@Library('jenkins-utils') _
```

- <b>Step 4:</b> To call the utility just instantiate the existing class in lib.

## Features in this utility

##### Validation if the commit originates from a PULL REQUEST

The method `validateLastMessageCommit` from class `GitMessageCommit` validates if the last commit originates from a
 pull request. Otherwise, it will return an exception and terminate the process.. 

Following usage example:

``` 
    stage('Git message validation'){
        when {
            branch 'master'
        }
        steps {
            script {
                new GitStage(this).validationGoal()
            }
        }
    }
```

##### Automatic branch tag in SEMVER pattern (https://semver.org/)

The method `generateNewTag` from class `GitTags` generate a new git tag taking into account the kind of last merge done.

- If you are a merge from a named release `RELEASE-MAJOR-VERSION`, increment X.0.0 
- If you are a merge from a release `Merged in release`, increment 0.X.0 
- If you are a merge from a hotfix `Merged in hotfix`, increment 0.0.X 

Following usage example:

``` 
    stage('New Git Tag Creation'){
        when {
            branch 'master'
        }
        steps {
            script {
                new GitStage(this).newTagGoal()
            }
        }
    }
```


## (PT_BR) Documentação ----------------------------------------
https://jenkins.io/doc/book/pipeline/shared-libraries/

Estrutura básica de `libraries` para utilização no Jenkins.

(root)
+- src                     # Groovy source files <br>
|   +- org<br>
|       +- foo<br>
|           +- Bar.groovy  # for org.foo.Bar class<br>
+- vars<br>
|   +- foo.groovy          # for global 'foo' variable<br>
|   +- foo.txt             # help for 'foo' variable<br>
+- resources               # resource files (external libraries only)<br>
|   +- org<br>
|       +- foo<br>
|           +- bar.json    # static helper data for org.foo.Bar<br>

## Utilização da libraries

- <b>Passo 1:</b> Na classe ``GitTags``, na constante ``CREDENTIALS_ID`` deve ser colocado o ID da credencial 
existente no Jenkins.

- <b>Passo 2:</b> No jenkins é necessário efetuar a configuração existente no link abaixo:
https://jenkins.io/doc/book/pipeline/shared-libraries/#global-shared-libraries

- <b>Passo 3:</b> Dentro do `Jenkinsfile`, incluir a instrução de importação da lib na
primeira linha do arquivo: 

```
@Library('jenkins-utils') _
```

- <b>Passo 4:</b> Para chamar o utilitário basta instanciar a classe existente 
na lib.

## Features existentes neste utilitário

##### Validação se o commit tem origem de um PULL REQUEST

O método `validateLastMessageCommit` da classe `GitMessageCommit` valida se o último commit
tem origem de um pull request. Caso contrario, ira retornar um exception e finalizar o processo. 

Segue exemplo de utilização:

``` 
    stage('Git message validation'){
        when {
            branch 'master'
        }
        steps {
            script {
                new GitStage(this).validationGoal()
            }
        }
    }
```

##### Tag automática da branch no padrão SEMVER (https://semver.org/)

O método `generateNewTag` da classe `GitTags` gera uma nova tag no git levando em consideração
o tipo do último merge feito. 

- Caso seja um merge a partir de uma release com nome `RELEASE-MAJOR-VERSION`, incrementa X.0.0 
- Caso seja um merge a partir de uma release `Merged in release`, incrementa 0.X.0 
- Caso seja um merge a partir de uma hotfix `Merged in hotfix`, incrementa 0.0.X 

Segue exemplo de utilização:

``` 
    stage('New Git Tag Creation'){
        when {
            branch 'master'
        }
        steps {
            script {
                new GitStage(this).newTagGoal()
            }
        }
    }
```
