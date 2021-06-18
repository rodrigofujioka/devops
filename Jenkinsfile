pipeline {
    agent any
    stages {
        stage ('Compilação') {

            steps {
                withMaven(maven : 'apache-maven-3.6.0') {
                    sh 'mvn clean compile'
                }
            }
        }
        stage ('Teste') {

            steps {
                withMaven(maven : 'apache-maven-3.6.0') {
                    sh 'mvn test'
                }
            }
        }
        stage ('Install') {
            steps {
                withMaven(maven : 'apache-maven-3.6.0') {
                    sh 'mvn install'
                }
            }
        }
        stage ('Build') {

            steps {
                withMaven(maven : 'apache-maven-3.6.0') {
                    sh 'mvn package'
                }
            }
        }
    }
}
