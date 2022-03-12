//// pipeline -> stages -> stage -> steps -> step
//properties([pipelineTriggers([githubPush()])])
//
//pipeline {
//  agent {label 'terraform && jenkins'}
//
//  environment{
//    TEST="google.com"
//    SSH=credentials('CENTOS_SSH')
//  }
//
//  //triggers { pollSCM('H/2 * * * *') }
//
//  parameters {
//    string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
//
//    text(name: 'BIOGRAPHY', defaultValue: '', description: 'Enter some information about the person')
//
//    booleanParam(name: 'TOGGLE', defaultValue: true, description: 'Toggle this value')
//
//    choice(name: 'CHOICE', choices: ['One', 'Two', 'Three'], description: 'Pick something')
//
//    password(name: 'PASSWORD', defaultValue: 'SECRET', description: 'Enter a password')
//  }
//
//  stages {
//
//    stage('Terraform INIT') {
//      steps {
//        sh 'echo Hello World'
//        sh 'echo'
//        sh 'echo ${TEST}'
//        sh 'echo ${SSH}'
//        //sh 'sleep 30'
//        sh 'echo ${PERSON}'
//      }
//    }
//
//    stage('Terraform Plan') {
//      steps {
//        sh 'echo Hello World'
//      }
//    }
//
//    stage('Terraform Apply') {
//      input {
//        message "Should we continue?"
//        ok "Yes, we should."
//        submitter "admin"
//      }
//      steps {
//        sh 'echo Hello World'
//      }
//    }
//
//    stage('Parallel Stages') {
//      parallel {
//
//        stage('One') {
//          steps {
//            sh 'sleep 30'
//          }
//        }
//
//        stage('Two') {
//          steps {
//            sh 'sleep 30'
//          }
//        }
//
//        stage('Three') {
//          steps {
//            sh 'sleep 40'
//          }
//        }
//
//      }
//    }
//
//  }
//
//
//  post {
//    always {
//      sh 'echo Hello'
//    }
//
//  }
//
//}
////
////

@Library('roboshop') _

//
//log.info 'Starting'
//log.warning 'Nothing to do!'

env.SAMPLE_URL="google.com"
log()

