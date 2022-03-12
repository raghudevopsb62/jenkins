//def info(message) {
//  echo "INFO: ${message}"
//}
//
//def warning(message) {
//  echo "WARNING: ${message}"
//}


def call() {
  if (! env.SAMPLE_URL) {
    env.SAMPLE_URL = "yahoo.com"
  }
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
