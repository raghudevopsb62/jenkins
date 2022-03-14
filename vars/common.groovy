def checkRelease() {
  stage('Check Release') {
    when {
      expression {
        GIT_BRANCH == "main"
      }
    }
    steps {
      script {
        def statusCode = sh script:"git ls-remote --tags origin | grep \$(cat VERSION | grep '^#' | head -1| sed -e 's|#|v|')", returnStatus:true
        if (statusCode == 0) {
          println "VERSION mentioned in main branch has already been tagged"
          skipRemainingStages = true
        }
      }
    }
  }


}

def createRelease() {
  def statusCode = sh script:"git ls-remote --tags origin | grep \$(cat VERSION | grep '^#' | head -1| sed -e 's|#|v|')", returnStatus:true
  if (statusCode == 0) {
    error "VERSION is already tagged, Use new version number"
  } else {
    sh '''
      mkdir temp 
      GIT_URL=$(echo $GIT_URL | sed -e "s|github.com|${TOKEN}@github.com|")
      cd temp
      git clone $GIT_URL .
      TAG=$(cat VERSION | grep "^#[0-9].[0-9].[0-9]" | head -1|sed -e "s|#|v|")
      git tag $TAG 
      git push --tags                  
    '''
  }
}

