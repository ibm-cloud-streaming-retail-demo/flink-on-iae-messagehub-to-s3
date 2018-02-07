#!/usr/bin/bash

# abort on error
set -e

FLINK_HOME=${pwd}/flink-1.4.0

# Download Flink - IAE as of 2018-01-29 is based on hadoop 2.7
wget -c -O flink-1.4.0-hadoop27-scala_2.11.tgz \
  "http://www.apache.org/dyn/mirrors/mirrors.cgi?action=download&filename=flink/flink-1.4.0/flink-1.4.0-bin-hadoop27-scala_2.11.tgz"

# Backup old flink installations
[[ -d flink-1.4.0 ]] && mv flink-1.4.0 flink-1.4.0-backup-$(date +%Y%m%d%H%M%S)

# Extract clean installation
tar xf flink-1.4.0-hadoop27-scala_2.11.tgz

# Is this ok? https://stackoverflow.com/questions/48505970/flink-on-yarn-could-not-initialize-class-org-apache-hadoop-fs-s3a-s3afilesyste
rm -f flink-1.4.0/lib/flink-shaded-hadoop2-uber-1.4.0.jar

mvn dependency:get -Ddest=./$FLINK_HOME/lib/ -Dartifact=org.apache.hadoop:hadoop-aws:2.7.3
mvn dependency:get -Ddest=./$FLINK_HOME/lib/ -Dartifact=com.amazonaws:aws-java-sdk-s3:1.11.183
mvn dependency:get -Ddest=./$FLINK_HOME/lib/ -Dartifact=com.amazonaws:aws-java-sdk-core:1.11.183
mvn dependency:get -Ddest=./$FLINK_HOME/lib/ -Dartifact=com.amazonaws:aws-java-sdk-kms:1.11.183
mvn dependency:get -Ddest=./$FLINK_HOME/lib/ -Dartifact=com.fasterxml.jackson.core:jackson-annotations:2.6.7
mvn dependency:get -Ddest=./$FLINK_HOME/lib/ -Dartifact=com.fasterxml.jackson.core:jackson-core:2.6.7
mvn dependency:get -Ddest=./$FLINK_HOME/lib/ -Dartifact=com.fasterxml.jackson.core:jackson-databind:2.6.7
mvn dependency:get -Ddest=./$FLINK_HOME/lib/ -Dartifact=joda-time:joda-time:2.8.1
mvn dependency:get -Ddest=./$FLINK_HOME/lib/ -Dartifact=org.apache.httpcomponents:httpcore:4.4.4
mvn dependency:get -Ddest=./$FLINK_HOME/lib/ -Dartifact=org.apache.httpcomponents:httpclient:4.5.3
