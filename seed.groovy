folder('Terraform') {
  displayName('Terraform')
  description('Terraform')
}

//
//jobs.each {
//  def newmap = it;
//  //println("    ${it.name}: ${it.git}");
//  x = it.git
//  freeStyleJob("Terraform/${it.name}") {
//    scm {
//      git {
//        remote {
//          name('origin')
//          url("https://github.com/raghudevopsb62/${x}.git")
//        }
//        branches('*/main')
//      }
//    }
//
//    steps {
//      shell('make')
//    }
//
//  }
//}
//


def jobs= [
    [name : "VPC", git : "terraform-vpc"],
    [name : "DB", git : "terraform-databases" ]
]


//jobs.each {
//  def newmap = it;
//  x = it.git
//
//  pipelineJob("Terraform/${it.name}") {
//    definition {
//      cpsScm {
//        scm {
//          git {
//            remote {
//              url("https://github.com/raghudevopsb62/${x}.git")
//            }
//          }
//        }
//        scriptPath('Jenkinsfile')
//      }
//    }
//  }
//
//}
//

multibranchPipelineJob('example') {
  triggers {
    cron('H/1 * * * *')
  }
  branchSources {
    git {
      id('123456789') // IMPORTANT: use a constant and unique identifier
      remote('https://github.com/raghudevopsb62/terraform-vpc.git')
      includes('**')
    }
  }
  orphanedItemStrategy {
    discardOldItems {
      numToKeep(0)
    }
  }
}
