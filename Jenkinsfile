pipeline {

    environment {
      registry = "sreeygcp/springboot_2_6_docker_repo"
      registryCredential = 'DockerHub_id'
      app = ''
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


    }

    parameters {

            string(name: 'dockerRepository',
                    defaultValue: 'sreeygcp/springboot_2_6_docker_repo',
                    description: 'The repository to push to')
            string(name: 'dockerRegistryCredentialsId',
                    defaultValue: 'DockerHub_id',
                    description: 'The Jenkins credentials id for docker registry to use')

            string(name: 'dockerRegistry', defaultValue: 'registry.hub.docker.com',
                  description: 'The docker registry to use (DNS name only)')

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
        }



       stage('Docker Build') {
       steps {
            withCredentials([usernamePassword(credentialsId: 'DockerHub_id')]) {
              sh "docker login -u sreeygcp -p aadvik5958"
              sh "docker build -t registry.hub.docker.com/sreeygcp/springboot_2_6_docker_repo/SpringBootRest:latest"
              sh "docker push registry.hub.docker.com/sreeygcp/springboot_2_6_docker_repo/SpringBootRest:latest"
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
