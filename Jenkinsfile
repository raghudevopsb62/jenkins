// pipeline -> stages -> stage -> steps -> step

pipeline {
  agent {label 'terraform && jenkins'}
  stages {

    stage('Terraform INIT') {
      steps {
        sh 'echo Hello World'
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
    sh 'Hello'
  }

}
