def call() {
  def skipRemainingStages = false

  if (! env.COMPONENT) {
    println "Component Missing"
    System.exit(1)
  }

  node('workstation') {

    environment {
      TOKEN = credentials('GITHUB_TOKEN')
    }

      common.checkRelease()

//      stage('Create Release') {
//        when {
//          allOf {
//            expression { GIT_BRANCH == "main" }
//            expression { !skipRemainingStages }
//          }
//
//        }
//        steps {
//          script {
//            common.createRelease()
//          }
//        }
//      }
//
//
//      stage('Download Dependencies') {
//        when {
//          expression {
//            !skipRemainingStages
//          }
//        }
//        steps {
//          sh '''
//          npm install
//        '''
//        }
//      }
//
//
//      stage('Make Artifacts') {
//        when {
//          expression {
//            !skipRemainingStages
//          }
//        }
//        steps {
//          sh '''
//          TAG=$(cat VERSION | grep "^#[0-9].[0-9].[0-9]" | head -1|sed -e "s|#|v|")
//          echo $TAG >version
//          zip -r ${COMPONENT}-${TAG}.zip node_modules server.js version
//        '''
//        }
//      }
//
//      stage('Publish Artifacts to Nexus') {
//        when {
//          expression {
//            !skipRemainingStages
//          }
//        }
//        steps {
//          sh '''
//          TAG=$(cat VERSION | grep "^#[0-9].[0-9].[0-9]" | head -1|sed -e "s|#|v|")
//          curl -f -v -u admin:admin123 --upload-file ${COMPONENT}-${TAG}.zip http://172.31.9.227:8081/repository/${COMPONENT}/${COMPONENT}-${TAG}.zip
//        '''
//        }
//      }
//
//
//
//    post {
//      always {
//        cleanWs()
//      }
//    }

  }
}