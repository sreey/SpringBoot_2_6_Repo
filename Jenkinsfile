pipeline {

    environment {
      registry = "sreeygcp/springboot_2_6_docker_repo"
      registryCredential = 'DockerHub_id'
      dockerImage = ''
    }
    agent {
        node {
            label 'master'
        }
    }

    options {
        buildDiscarder logRotator(
                    daysToKeepStr: '16',
                    numToKeepStr: '10'
            )

            parameters{
                    text(name: 'dockerRegistry',
                            defaultValue: 'registry.hub.docker.com',
                            description: 'The docker registry to use (DNS name only)',),
                    text(name: 'dockerRepository',
                            defaultValue: 'sreeygcp/springboot_2_6_docker_repo',
                            description: 'The repository to push to',),
                    text(name: 'dockerRegistryCredentialsId',
                            defaultValue: 'DockerHub_id',
                            description: 'The Jenkins credentials id for docker registry to use',)
            }
    }



    stages {

        stage('Cleanup Workspace') {
            steps {
                cleanWs()
                sh """
                echo "Cleaned Up Workspace For Project"
                """
            }
        }

        stage('Code Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[url: 'https://github.com/sreey/SpringBoot_2_6_Repo.git']]
                ])
            }
        }

        stage(' Unit Testing') {
            steps {
                sh """
                echo "Running Unit Tests"
                """
            }
        }

        stage('Code Analysis') {
            steps {
                sh """
                echo "Running Code Analysis"
                """
            }
        }

        stage ('Initialize') {
            steps {
                sh '''
                    echo "PWD = ${PWD}"
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }

        stage ('Build') {
            steps {
                sh '''
                      cd SpringBootRest
                      mvn clean install
                '''
            }
            post {
                success {
                    sh '''
                        echo "Running build post success Analysis"

                          cd SpringBootRest
                          echo "PWD = ${PWD}"
                    '''
                //    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }

        stage('Building Docker image') {
            steps {
                sh '''
                      cd SpringBootRest
                      mvn docker:build
                '''
            }

        }

       stage('Docker Build') {
       steps {
          script {
            docker.withRegistry("https://${dockerRegistry}", "${dockerRegistryCredentialsId}") {
               image = docker.build("${dockerRegistry}/${dockerRepository}", "--pull --no-cache .")
               image.push()
            }
          }
          }
       }


        stage('Cleaning up') {
            steps {
            sh """
            echo "Running Cleaning Up"
            """

            }
        }
    }


}
