#!/bin/bash

cat <<EOF
org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject:
  actions: ''
  description: ''
  properties: ''
  folderViews:
    owner:
      _class: org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject
      _reference: ../..
    _class: jenkins.branch.MultiBranchProjectViewHolder
  healthMetrics:
    com.cloudbees.hudson.plugins.folder.health.WorstChildHealthMetric:
      nonRecursive: 'false'
  icon:
    owner:
      _class: org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject
      _reference: ../..
    _class: jenkins.branch.MetadataActionFolderIcon
  orphanedItemStrategy:
    pruneDeadBranches: 'true'
    daysToKeep: '-1'
    numToKeep: '-1'
    abortBuilds: 'false'
    _class: com.cloudbees.hudson.plugins.folder.computed.DefaultOrphanedItemStrategy
  triggers:
    com.cloudbees.hudson.plugins.folder.computed.PeriodicFolderTrigger:
      spec: '* * * * *'
      interval: 'INTERNAL'
  disabled: 'false'
  sources:
    data:
      jenkins.branch.BranchSource:
        source:
          id: 'UNIQUEID'
          remote: GIT_URL
          credentialsId: ''
          traits:
            jenkins.plugins.git.traits.BranchDiscoveryTrait: ''
            jenkins.scm.impl.trait.WildcardSCMHeadFilterTrait:
              includes: '**'
              excludes: ''
          _class: jenkins.plugins.git.GitSCMSource
        strategy:
          properties:
            _class: empty-list
          _class: jenkins.branch.DefaultBranchPropertyStrategy
    owner:
      _class: org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject
      _reference: ../..
    _class: jenkins.branch.MultiBranchProject$BranchSourceList
  factory:
    owner:
      _class: org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject
      _reference: ../..
    scriptPath: Jenkinsfile
    _class: org.jenkinsci.plugins.workflow.multibranch.WorkflowBranchProjectFactory
EOF >/tmp/job.yaml
