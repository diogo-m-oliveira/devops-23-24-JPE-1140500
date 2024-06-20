# DevOps Technical Report | Class Assignment 5

## CI/CD Pipelines with Jenkins - Part 1

### Introduction
The first goal of this assignment is to practice creating a pipeline with Jenkins using the ”gradle basic
demo” code.

### Requirements
We should define the following stages in your pipeline:
1. Checkout. To check out the code form the repository.
2. Assemble. Compiles and Produces the archive files with the application. Do not use
the build task of gradle (because it also executes the tests)!
3. Test. Executes the Unit Tests and publish in Jenkins the Test results. See the junit
step for further information on how to archive/publish test results.
    * Do not forget to add some unit tests to the project (maybe you already have done it).
4. Archive. Archives in Jenkins the archive files (generated during Assemble).

### Implementation
1. Add a Jenkinsfile to the repository (in this case, ca5/part1). Make sure to correctly specify the branch
and the URL of the repository in the 'Checkout' stage, and the directory of the gradlew script in the
'Assemble', 'Test', and 'Archive' stages. Also, allow the execution of the gradlew script by changing
the permissions of the file.

```groovy
pipeline {
   agent any

   stages {
      stage('Checkout') {
         steps {
            echo 'Checking out the code from the repository'
            git branch: 'main', url: 'https://github.com/diogo-m-oliveira/devops-23-24-JPE-1140500.git'
         }
      }
      stage('Assemble') {
         steps {
            echo 'Assembling...'
            dir('ca2/part1') {
               script {
                  if (isUnix()) {
                     sh 'chmod +x ./gradlew'
                     sh './gradlew clean assemble'
                  } else {
                     bat 'gradlew.bat clean assemble'
                  }
               }
            }
         }
      }
      stage('Test') {
         steps {
            echo 'Testing...'
            dir('ca2/part1') {
               script {
                  if (isUnix()) {
                     sh './gradlew test'
                  } else {
                     bat 'gradlew.bat test'
                  }
               }
               junit 'build/test-results/test/*.xml'
            }
         }
      }
      stage('Archive') {
         steps {
            echo 'Archiving...'
            dir('ca2/part1') {
               archiveArtifacts 'build/libs/*.jar'
            }
         }
      }
   }
}

```

#### IMPORTANT!
Make sure your gradle wrapper script version is compatible with the Java version running on Jenkins.
In this case, the gradlew script had to be updated to version 8.6.

```bash
./gradlew wrapper --gradle-version=8.6
```

Make sure the `gradle-wrapper.properties` file in /gradle/wrapper is updated with the correct version.
```properties
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.6-bin.zip
```
2. Start Jenkins (if installed in Windows)
```bash
cd [Jenkins War File Directory]
java -jar jenkins.war
```

3. Create a new pipeline in Jenkins and configure it to use the Jenkinsfile in the repository.
    * Go to Jenkins and click on 'New Item'.
    * Enter a name for the pipeline and select 'Pipeline'.
    * In the 'Pipeline' section, select 'Pipeline script from SCM'.
    * Choose 'Git' as the SCM and enter the repository URL.
    * Place the repository URL in the 'Repository URL' field.
   
      ![Pipeline_setup_Jenkins](images/Pipeline_setup_Jenkins_1.png)

    * Specify the branch to be used in the 'Branch Specifier' field.
    * In the 'Script Path' field, enter the correct location of the Jenkinsfile.
   
      ![Pipeline_setup_Jenkins](images/Pipeline_setup_Jenkins_2.png)
   
    * Click 'Save' to create the pipeline.

4. Run the pipeline and verify that the stages are executed correctly (console output).

   ![Pipeline_build_Jenkins](images/Pipeline_build_history.png)

   ![Pipeline_build_output_Jenkins](images/Pipeline_build_console_output.png)

5. Check the results of the pipeline in Jenkins.

   ![Pipeline_results_Jenkins](images/Pipeline_results.png)


## CI/CD Pipelines with Jenkins - Part 2

### Introduction
The goal of this assignment is to create a pipeline with Jenkins that builds a Docker image of the Spring Boot application and pushes it to Docker Hub.

### Requirements
You should define the following stages in your pipeline:
1. Checkout. To checkout the code from the repository
2. Assemble. Compiles and Produces the archive files with the application. 
3. Test. Executes the Unit Tests and publish in Jenkins the Test results. See the junit
step for further information on how to archive/publish test results.
4. Javadoc. Generates the javadoc of the project and publish it in Jenkins.
5. Archive. Archives in Jenkins the archive files (generated during Assemble, i.e., the war file)
6. Publish Image. Generate a docker image with Tomcat and the war file and publish it
in the Docker Hub.

### Implementation
1. Add a Jenkinsfile to the repository (in this case, ca5/part2). Make sure to correctly specify the branch
and the URL of the repository in the 'Checkout' stage, and the directory of the gradlew script in the
'Assemble', 'Test', and 'Javadoc' stages. Also, allow the execution of the
gradlew script by changing the permissions of the file.
Also, make sure the Docker Hub credentials ID and the Docker image name are correctly defined in the environment section
(the docker image must be of format "YOUR-DOCKER-ID/IMAGENAME").

```groovy
pipeline {
    agent any

    environment {
        // Define Docker Hub credentials ID as configured in Jenkins credentials
        DOCKERHUB_CREDENTIALS = 'sabesquemeoboda'
        // Define the Docker image name
        IMAGE_NAME = "sabesquemeoboda/image-generated-with-jenkins"
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out the code from the repository'
                git branch: 'main', url: 'https://github.com/diogo-m-oliveira/devops-23-24-JPE-1140500.git'
            }
        }

        stage('Assemble') {
            steps {
                         echo 'Assembling...'
                dir('ca2/part2') {
                    script {
                        // Ensure the Gradle Wrapper is executable (Unix) or available (Windows)
                        if (isUnix()) {
                            sh 'chmod +x ./gradlew'
                            sh './gradlew clean assemble -x test'
                        } else {
                            bat 'gradlew.bat clean assemble -x test'
                        }
                    }
                }
            }
            post {
                success {
                    // Archive the build artifacts
                    archiveArtifacts artifacts: '**/build/libs/*.jar', allowEmptyArchive: true
                }
            }
        }

        stage('Test') {
            steps {

                echo 'Testing...'
                dir('ca2/part2') {
                    script {
                        // Ensure the Gradle Wrapper is executable (Unix) or available (Windows)
                        if (isUnix()) {
                            sh './gradlew test'
                        } else {
                            bat 'gradlew.bat test'
                        }
                    }
                    // Publish JUnit test results
                    junit '**/build/test-results/test/*.xml'
                }
            }
            post {
                always {
                    // Archive the JUnit test results
                    junit '**/build/test-results/test/TEST-*.xml'
                }
            }
        }

        stage('Javadoc') {
            steps {

                echo 'Generating Javadoc...'
                dir('ca2/part2') {
                    script {
                        // Ensure the Gradle Wrapper is executable (Unix) or available (Windows)
                        if (isUnix()) {
                            sh './gradlew javadoc'
                        } else {
                            bat 'gradlew.bat javadoc'
                        }
                    }
                    // Archive and publish Javadoc
                    archiveArtifacts artifacts: '**/build/docs/javadoc/**', allowEmptyArchive: true
                    publishHTML target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'build/docs/javadoc',
                        reportFiles: 'index.html',
                        reportName: 'Javadoc Report'
                    ]
                }
            }
        }

        stage('Archive') {
            steps {

                echo 'Archiving...'
                dir('ca2/part2') {
                    // Archive build artifacts
                    archiveArtifacts artifacts: '**/build/libs/*.jar', allowEmptyArchive: true
                }
            }
        }

        stage('Publish Image') {
            steps {

                echo 'Publishing Docker image...'
                script {
                def dockerImage = docker.build("${IMAGE_NAME}:${env.BUILD_NUMBER}", '--progress=plain ca2/part2/')
                    docker.withRegistry('https://registry.hub.docker.com', DOCKERHUB_CREDENTIALS) {

                        dockerImage.push()
                    }
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
```

2. In Jenkins, add your Docker credentials to Jenkins. Go to 'Manage Jenkins' -> 'Manage Credentials' -> 'Global credentials' -> 'Add Credentials'.
Select 'Username with password' as the kind, enter your Docker Hub username and password, and give it an ID.

   ![Docker_Credentials_Jenkins](images/Docker_Credentials_Jenkins.png)

3. Install the 'Docker Pipeline', 'Docker', and 'HTML Publisher' plugins in Jenkins.
Go to 'Manage Jenkins' -> 'Manage Plugins' -> 'Available' -> search for the plugins and click -> install.

3. Create a new pipeline in Jenkins and configure it to use the Jenkinsfile in the repository.
    * Go to Jenkins and click on 'New Item'.
    * Enter a name for the pipeline and select 'Pipeline'.
    * In the 'Pipeline' section, select 'Pipeline script from SCM'.
    * Choose 'Git' as the SCM and enter the repository URL.
    * Place the repository URL in the 'Repository URL' field.
    * Specify the branch to be used in the 'Branch Specifier' field.
    * In the 'Script Path' field, enter the correct location of the Jenkinsfile.
    * Click 'Save' to create the pipeline.

4. Add the Dockerfile to the repository (in this case, ca2/part2).
```dockerfile
FROM tomcat:9-jdk17-openjdk

COPY ./build/libs/*.jar /usr/local/tomcat/webapps/

EXPOSE 8080
```

5. Run the pipeline and verify that the stages are executed correctly (console output).

   ![Pipeline_build_output_Jenkins](images/Pipeline_build_console_output_part2.png)

6. Verify that the Docker image was pushed to Docker Hub.

   ![Docker_Hub_Image](images/Docker_Hub_Image.png)