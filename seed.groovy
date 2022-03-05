freeStyleJob('example') {
  scm {
    git {
      remote {
        name('origin')
        url('https://github.com/raghudevopsb62/ansible.git')
      }
    }
  }

  steps {
    shell('ls -ltr')
  }

}

