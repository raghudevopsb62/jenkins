// pipeline -> stages -> stage -> steps -> step

pipeline {
  agent none
  stages {

    stage('Terraform INIT') {
      agent { node { label 'workstation' } }
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
}
