folder('Terraform') {
  displayName('Terraform')
  description('Terraform')
}

freeStyleJob('Terraform/VPC') {
  scm {
    git {
      remote {
        name('origin')
        url('https://github.com/raghudevopsb62/terraform-vpc.git')
      }
    }
  }

  steps {
    shell('make dev')
  }

}

freeStyleJob('Terraform/ALB') {
  scm {
    git {
      remote {
        name('origin')
        url('https://github.com/raghudevopsb62/terraform-mutable-alb.git')
      }
    }
  }

  steps {
    shell('make')
  }

}

freeStyleJob('Terraform/DB') {
  scm {
    git {
      remote {
        name('origin')
        url('https://github.com/raghudevopsb62/terraform-databases.git')
      }
    }
  }

  steps {
    shell('make')
  }

}

