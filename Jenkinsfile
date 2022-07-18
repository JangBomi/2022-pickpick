pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps {
        git(branch: 'test/jenkins', url: 'https://github.com/woowacourse-teams/2022-pickpick.git')
      }
    }

    stage('Build') {
      steps {
        sh '''cd backend
chmod +x ./gradlew
./gradlew init
./gradlew build'''
      }
    }

    stage('Test') {
      steps {
        sh '''./gradlew test
'''
      }
    }

  }
  tools {
    gradle 'gradle'
  }
}