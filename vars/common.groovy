def checkRelease() {
  stage('Check Release') {
    NODE = 'workstation'
    if (env.BRANCH_NAME == "main") {
        sh 'pwd'
        sh 'ls -l '
        def statusCode = sh script:"git ls-remote --tags origin | grep \$(cat VERSION | grep '^#' | head -1| sed -e 's|#|v|')", returnStatus:true
        if (statusCode == 0) {
          println "VERSION mentioned in main branch has already been tagged"
          env.skipRemainingStages = true
        }
    } else {
      println 'none worked'
      sh 'env'
    }
  }
}

def createRelease(String GIT_URL) {
  env.GIT_URL = GIT_URL
  if (! env.skipRemainingStages) {
    stage('Create Release to GIT Tags') {
      def statusCode = sh script: "git ls-remote --tags origin | grep \$(cat VERSION | grep '^#' | head -1| sed -e 's|#|v|')", returnStatus: true
      if (statusCode == 0) {
        error "VERSION is already tagged, Use new version number"
      } else {
          withCredentials([string(credentialsId: 'GITHUB_TOKEN', variable: 'TOKEN')]) {
          sh '''
            rm -rf temp && mkdir temp 
            GIT_URL=$(echo $GIT_URL | sed -e "s|github.com|${TOKEN}@github.com|")
            cd temp
            git clone $GIT_URL .
            TAG=$(cat VERSION | grep "^#[0-9].[0-9].[0-9]" | head -1|sed -e "s|#|v|")
            git tag $TAG 
            git push --tags                  
          '''
          }
      }
    }
  }
}

def publishArtifacts() {
  if (!env.skipRemainingStages) {
    stage('Make Artifacts to NEXUS') {
      withCredentials([usernameColonPassword(credentialsId: 'NEXUS', variable: 'USERPASS')]) {
        sh '''
          TAG=$(cat VERSION | grep "^#[0-9].[0-9].[0-9]" | head -1|sed -e "s|#|v|")
          curl -f -v -u ${USERPASS} --upload-file ${COMPONENT}-${TAG}.zip http://172.31.9.227:8081/repository/${COMPONENT}/${COMPONENT}-${TAG}.zip
        '''
      }

    }
  }
}

def unitTests() {
  stage('Run Unit Tests') {
    println 'Running Unit Tests'
  }
}

def integrationTests() {
  stage('Run Integration Tests') {
    println 'Running Integration Tests'
  }
}


def sonarScan() {
  stage('Sonar Scan') {
    withCredentials([string(credentialsId: 'SONAR', variable: 'TOKEN')]) {
      sh '''               
        #sonar-scanner -Dsonar.login=${TOKEN} -Dsonar.projectKey=${COMPONENT} -Dsonar.sources=. -Dsonar.host.url=http://172.31.2.202:9000
        #sonar-quality-gate.sh admin admin123 172.31.2.202 ${COMPONENT}
        echo Sonar tests
      '''
    }
  }
}

def publishAMI() {
  if (!env.skipRemainingStages) {
    stage('Publish AMI') {
      ansiColor('xterm') {
        sh '''
          export TF_VAR_APP_VERSION=$(cat VERSION | grep "^#[0-9].[0-9].[0-9]" | head -1|sed -e "s|#||")
          terraform init 
          terraform apply -auto-approve 
          terraform state rm module.ami.aws_ami_from_instance.ami
          terraform destroy -auto-approve
        '''
      }
    }
  }
}


def dockerBuild() {
  if (!env.skipRemainingStages) {
    stage('Docker Build') {
      ansiColor('xterm') {
        sh '''
          APP_VERSION=$(cat VERSION | grep "^#[0-9].[0-9].[0-9]" | head -1|sed -e "s|#||")
          docker build -t 739561048503.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:${APP_VERSION} . 
        '''
      }
    }
  }
}

def dockerPush() {
  if (!env.skipRemainingStages) {
    stage('Docker Build') {
      ansiColor('xterm') {
        sh '''
          aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 739561048503.dkr.ecr.us-east-1.amazonaws.com
          APP_VERSION=$(cat VERSION | grep "^#[0-9].[0-9].[0-9]" | head -1|sed -e "s|#||")
          docker push 739561048503.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:${APP_VERSION} 
        '''
      }
    }
  }
}

