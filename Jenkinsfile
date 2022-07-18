pipeline
{
    agent any

    tools {
        gradle 'gradle'
    }

    stages
    {
        stage ('Checkout')
        {
            steps {
                git branch: 'test/jenkins', url: 'https://github.com/woowacourse-teams/2022-pickpick.git'
            }
        }
        stage ('Build')
        {
            steps {
                sh './backend/gradlew bootJar'
            }
        }
        stage('JUnit Test'){
            steps{
                junit '**/backend/build/test-results/test/*.xml'
            }
        }
    }
}
