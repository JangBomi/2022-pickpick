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
                sh './gradlew bootJar'
            }
        }
        stage('JUnit Test'){
            steps{
                junit '**/build/test-results/test/*.xml'
            }
        }
    }
}
