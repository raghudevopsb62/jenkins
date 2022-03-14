def call() {
  def skipRemainingStages = false

  if (! env.COMPONENT) {
    println "Component Missing"
    System.exit(1)
  }
  NODE = null
  node {

    environment {
      TOKEN = credentials('GITHUB_TOKEN')
    }

    def s = checkout scm
    //common.checkRelease()
    //common.createRelease(s.GIT_URL)

    if(env.TYPE == "nodejs") {
      nodejs.downloadDependencies()
      nodejs.makeArtifacts()
    }

    common.publishArtifacts() 

  }
}