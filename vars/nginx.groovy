def makeArtifacts() {
  if (!env.skipRemainingStages) {
    stage('Make Artifacts') {
      sh '''
        TAG=$(cat VERSION | grep "^#[0-9].[0-9].[0-9]" | head -1|sed -e "s|#|v|")
        echo $TAG >version
        cd static
        zip -r ../${COMPONENT}-${TAG}.zip ../version  *
        ls -ltr
      '''
    }
  }
}

