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

    common.unitTests()
    common.integrationTests()
    if(env.TYPE == "nodejs") {
      nodejs.downloadDependencies()
      nodejs.makeArtifacts()
    }

    if(env.TYPE == "maven") {
      maven.makePackage()
      maven.makeArtifacts()
    }

    if(env.TYPE == "python") {
      python.makeArtifacts()
    }

    if(env.TYPE == "nginx") {
      nginx.makeArtifacts()
    }

    common.publishArtifacts()
    common.createRelease(s.GIT_URL)

  }
}
