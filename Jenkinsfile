pipeline {
    agent any
    tools {
        jdk 'jdk8'
        maven 'M3'
    }
    stages {
        stage('Checkout') {
            steps {
                git changelog: false,
                url: 'https://github.com/bugtrio/selenium_simple_example.git',
                branch: 'main'
            }
        }
        stage('Execution') {
            steps {
                sh "mvn clean test"
            }
        }
    }
    post {
        failure {
            mail
            to: 'teambugtrio@gmail.com',
                    subject: "Erro na Pipeline: ${currentBuild.fullDisplayName}",
                    body: "URL: ${env.BUILD_URL}"
        }
    }
}