pipeline {
    agent any
    stages {
        stage ('Compile') {
            steps {
                withMaven {
                    sh 'mvn clean compile'
                }
            }
        }
        stage ('Teste') {
            steps {
                withMaven {
                    sh 'mvn test'
                }
            }
        }
        stage ('qualidade') {
            steps {
                withMaven {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        stage ('Install') {
            steps {
                withMaven {
                    sh 'mvn install'
                }
            }
        }
        stage ('Build') {
            steps {
                withMaven {
                    sh 'mvn package'
                }
            }
        }

    }
}
