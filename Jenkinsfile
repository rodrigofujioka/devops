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
