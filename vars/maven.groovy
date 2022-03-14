def makePackage() {
  if (!env.skipRemainingStages) {
    stage('Download Dependencies') {
      sh 'mvn clean package'
    }
  }
}

def makeArtifacts() {
  if (!env.skipRemainingStages) {
    stage('Make Artifacts') {
      sh '''
        TAG=$(cat VERSION | grep "^#[0-9].[0-9].[0-9]" | head -1|sed -e "s|#|v|")
        echo $TAG >version
        cp target/${COMPONENT}-1.0.jar ${COMPONENT}.jar
        zip -r ${COMPONENT}-${TAG}.zip ${COMPONENT}.jar version
        ls -ltr
      '''
    }
  }
}

