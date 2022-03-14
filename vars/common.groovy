def checkRelease() {
  def statusCode = sh script:"git ls-remote --tags origin | grep \$(cat VERSION | grep '^#' | head -1| sed -e 's|#|v|')", returnStatus:true
  if (statusCode == 0) {
    println "VERSION mentioned in main branch has already been tagged"
    skipRemainingStages = true
  }
}

