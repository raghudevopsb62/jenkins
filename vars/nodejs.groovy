def downloadDependencies() {
  if (!env.skipRemainingStages) {
    stage('Download Dependencies') {
      sh 'npm install'
    }
  }
}