//def info(message) {
//  echo "INFO: ${message}"
//}
//
//def warning(message) {
//  echo "WARNING: ${message}"
//}

def call() {
  pipeline {
    agent none
    stages {
      stage ('Example') {
        steps {
          // log.info 'Starting'
          script {
            print 'hello'
            print "SAMPLE URL = ${SAMPLE_URL}"
          }
        }
      }
    }
  }
}
