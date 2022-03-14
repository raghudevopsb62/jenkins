def call() {
  def skipRemainingStages = false

  if (! env.COMPONENT) {
    println "Component Missing"
    System.exit(1)
  }
  NODE = null
  node {

    sh 'find . | grep -v \'^.$\'  | xargs rm -rf'
    def s = checkout scm
    common.checkRelease()
    common.createRelease(s.GIT_URL)

    if(env.TYPE == "nodejs") {
      nodejs.downloadDependencies()
      nodejs.makeArtifacts()
    }

    common.publishArtifacts()

  }
}
