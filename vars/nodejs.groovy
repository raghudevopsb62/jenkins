def downloadDependencies() {
  if (!env.skipRemainingStages) {
    stage('Download Dependencies') {
      sh 'npm install'
    }
  }
}

def makeArtifacts() {
  if (!env.skipRemainingStages) {
    stage('Make Artifacts') {
      sh '''
        TAG=$(cat VERSION | grep "^#[0-9].[0-9].[0-9]" | head -1|sed -e "s|#|v|")
        echo $TAG >version
        zip -r ${COMPONENT}-${TAG}.zip node_modules server.js version
        ls -ltr
      '''
    }
  }
}

