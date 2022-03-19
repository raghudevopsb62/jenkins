def call() {
  pipeline {

    agent {
      node { label 'workstation' }
    }

    parameters {
      string(name: 'APPLY_ENV', defaultValue: 'dev', description: 'On which env you want to run?')
    }

    options {
      ansiColor('xterm')
    }

    stages {

      stage('Terraform Init') {
        steps {
          sh '''
            cd mutable
            terrafile -f env-${APPLY_ENV}/Terrafile
            export TF_VAR_APP_VERSION=`cat ../VERSION | grep ^# | head -1 | sed -e 's/#//'`
            terraform init -backend-config=env-${APPLY_ENV}/backend.tfvars
          '''
        }
      }

      stage('Terraform Plan') {
        steps {
          sh '''
            cd mutable
            export TF_VAR_APP_VERSION=`cat ../VERSION | grep ^# | head -1 | sed -e 's/#//'`
            terraform plan -var-file=env-${APPLY_ENV}/main.tfvars
          '''
        }
      }

      stage('Terraform Apply') {
        steps {
          sh '''
            cd mutable
            export TF_VAR_APP_VERSION=`cat ../VERSION | grep ^# | head -1 | sed -e 's/#//'`
            terraform apply -auto-approve -var-file=env-${APPLY_ENV}/main.tfvars
          '''
        }
      }

    }

    post {
      always {
        cleanWs()
      }
    }

  }

//
}
