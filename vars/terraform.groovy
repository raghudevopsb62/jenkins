def call() {
  pipeline {

    agent {
      node { label 'workstation' }
    }

    parameters {
      string(name: 'APPLY_ENV', defaultValue: 'dev', description: 'On which env you want to run?')
      string(name: 'APP_VERSION', defaultValue: '0.0.0', description: 'Which App Version?')
    }

    options {
      ansiColor('xterm')
    }

    stages {

      stage('Terraform Init') {
        steps {
          sh '''
            cd immutable
            terrafile -f env-${APPLY_ENV}/Terrafile
            export TF_VAR_APP_VERSION=`cat ../VERSION | grep ^# | head -1 | sed -e 's/#//'`
            terraform init -backend-config=env-${APPLY_ENV}/backend.tfvars
          '''
        }
      }

      stage('Terraform Plan') {
        steps {
          sh '''
            cd immutable
            export TF_VAR_APP_VERSION=`cat ../VERSION | grep ^# | head -1 | sed -e 's/#//'`
            terraform plan -var-file=env-${APPLY_ENV}/main.tfvars -var APP_VERSION=${APP_VERSION}
          '''
        }
      }

      stage('Terraform Apply') {
        steps {
          sh '''
            cd immutable
            #export TF_VAR_APP_VERSION=`cat ../VERSION | grep ^# | head -1 | sed -e 's/#//'` 
            terraform apply -auto-approve -var-file=env-${APPLY_ENV}/main.tfvars -var APP_VERSION=${APP_VERSION}
          '''
        }
      }

      stage('Instance Refresh') {
        steps {
          sh '''
            aws autoscaling start-instance-refresh --auto-scaling-group-name ${COMPONENT}-${APPLY_ENV} --preferences \'{"InstanceWarmup": 120, "MinHealthyPercentage": 50}\'
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
