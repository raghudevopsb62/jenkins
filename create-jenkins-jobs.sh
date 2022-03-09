#!/bin/bash

cat <<EOF >/tmp/jobs
Terraform,VPC,https://github.com/raghudevopsb62/terraform-vpc,123456780,120000
Terraform,DB,https://github.com/raghudevopsb62/terraform-databases,123456781,120000
EOF

for job in $(cat /tmp/jobs); do
cat <<EOF >/tmp/folder.xml
<?xml version="1.0" encoding="UTF-8"?><com.cloudbees.hudson.plugins.folder.Folder>
    <actions/>
    <properties/>
    <icon class="com.cloudbees.hudson.plugins.folder.icons.StockFolderIcon"/>
    <folderViews class="com.cloudbees.hudson.plugins.folder.views.DefaultFolderViewHolder">
        <views>
            <hudson.model.AllView>
                <owner class="com.cloudbees.hudson.plugins.folder.Folder" reference="../../../.."/>
                <name>all</name>
                <filterExecutors>false</filterExecutors>
                <filterQueue>false</filterQueue>
                <properties class="hudson.model.View\$PropertyList"/>
            </hudson.model.AllView>
        </views>
        <tabBar class="hudson.views.DefaultViewsTabBar"/>
        <primaryView>all</primaryView>
    </folderViews>
    <healthMetrics>
        <com.cloudbees.hudson.plugins.folder.health.WorstChildHealthMetric/>
    </healthMetrics>
    <displayName>FOLDER</displayName>
    <description>FOLDER</description>
</com.cloudbees.hudson.plugins.folder.Folder>
EOF

cat <<EOF >/tmp/job.xml
  <?xml version='1.1' encoding='UTF-8'?>
  <org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject plugin="workflow-multibranch@711.vdfef37cda_816">
    <actions/>
    <description></description>
    <properties/>
    <folderViews class="jenkins.branch.MultiBranchProjectViewHolder" plugin="branch-api@2.7.0">
      <owner class="org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject" reference="../.."/>
    </folderViews>
    <healthMetrics>
      <com.cloudbees.hudson.plugins.folder.health.WorstChildHealthMetric plugin="cloudbees-folder@6.708.ve61636eb_65a_5">
        <nonRecursive>false</nonRecursive>
      </com.cloudbees.hudson.plugins.folder.health.WorstChildHealthMetric>
    </healthMetrics>
    <icon class="jenkins.branch.MetadataActionFolderIcon" plugin="branch-api@2.7.0">
      <owner class="org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject" reference="../.."/>
    </icon>
    <orphanedItemStrategy class="com.cloudbees.hudson.plugins.folder.computed.DefaultOrphanedItemStrategy" plugin="cloudbees-folder@6.708.ve61636eb_65a_5">
      <pruneDeadBranches>true</pruneDeadBranches>
      <daysToKeep>-1</daysToKeep>
      <numToKeep>-1</numToKeep>
      <abortBuilds>false</abortBuilds>
    </orphanedItemStrategy>
    <triggers>
      <com.cloudbees.hudson.plugins.folder.computed.PeriodicFolderTrigger plugin="cloudbees-folder@6.708.ve61636eb_65a_5">
        <spec>* * * * *</spec>
        <interval>INTERVAL</interval>
      </com.cloudbees.hudson.plugins.folder.computed.PeriodicFolderTrigger>
    </triggers>
    <disabled>false</disabled>
    <sources class="jenkins.branch.MultiBranchProject\$BranchSourceList" plugin="branch-api@2.7.0">
      <data>
        <jenkins.branch.BranchSource>
          <source class="jenkins.plugins.git.GitSCMSource" plugin="git@4.10.3">
            <id>JOB_ID</id>
            <remote>GIT_URL</remote>
            <credentialsId></credentialsId>
            <traits>
              <jenkins.plugins.git.traits.BranchDiscoveryTrait/>
              <jenkins.scm.impl.trait.WildcardSCMHeadFilterTrait plugin="scm-api@595.vd5a_df5eb_0e39">
                <includes>**</includes>
                <excludes></excludes>
              </jenkins.scm.impl.trait.WildcardSCMHeadFilterTrait>
            </traits>
          </source>
          <strategy class="jenkins.branch.DefaultBranchPropertyStrategy">
            <properties class="empty-list"/>
          </strategy>
        </jenkins.branch.BranchSource>
      </data>
      <owner class="org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject" reference="../.."/>
    </sources>
    <factory class="org.jenkinsci.plugins.workflow.multibranch.WorkflowBranchProjectFactory">
      <owner class="org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject" reference="../.."/>
      <scriptPath>Jenkinsfile</scriptPath>
    </factory>
  </org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject>
EOF

  JOB_ID=$(echo $job | awk -F , '{print $4}')
  DIR=$(echo $job | awk -F , '{print $1}')
  NAME=$(echo $job | awk -F , '{print $2}')
  JOB_NAME="$DIR/$NAME"
  GIT_URL=$(echo $job | awk -F , '{print $3}')
  INTERVAL=$(echo $job | awk -F , '{print $5}')

  sed -i -e "s|INTERVAL|${INTERVAL}|" -e "s|GIT_URL|${GIT_URL}|" -e "s|JOB_ID|${JOB_ID}|" /tmp/job.xml
  sed -i -e "s|FOLDER|${DIR}|"  /tmp/folder.xml
  cat /tmp/folder.xml | java -jar ~/jenkins-cli.jar -auth admin:admin -s http://172.31.14.253:8080/ -webSocket create-job ${DIR}
  cat /tmp/job.xml | java -jar ~/jenkins-cli.jar -auth admin:admin -s http://172.31.14.253:8080/ -webSocket create-job ${JOB_NAME}
done
