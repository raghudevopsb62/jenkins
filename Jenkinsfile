// pipeline -> stages -> stage -> steps -> step

pipeline {
  agent {label 'terraform && jenkins'}

  environment{
    TEST="google.com"
    SSH=credentials('CENTOS_SSH')
  }

  parameters {
    string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')

    text(name: 'BIOGRAPHY', defaultValue: '', description: 'Enter some information about the person')

    booleanParam(name: 'TOGGLE', defaultValue: true, description: 'Toggle this value')

    choice(name: 'CHOICE', choices: ['One', 'Two', 'Three'], description: 'Pick something')

    password(name: 'PASSWORD', defaultValue: 'SECRET', description: 'Enter a password')
  }

  stages {

    stage('Terraform INIT') {
      steps {
        sh 'echo Hello World'
        sh 'echo'
        sh 'echo ${TEST}'
        sh 'echo ${SSH}'
        //sh 'sleep 30'
        sh 'echo ${PERSON}'
      }
    }

    stage('Terraform Plan') {
      steps {
        sh 'echo Hello World'
      }
    }

    stage('Terraform Apply') {
      steps {
        sh 'echo Hello World'
      }
    }

  }


  post {
    always {
      sh 'echo Hello'
    }

  }

}
