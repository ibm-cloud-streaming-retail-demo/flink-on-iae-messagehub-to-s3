#!/bin/bash

# abort on error
set -e

source /home/clsadmin/credentials.sh

FLINK_HOME=$(pwd)/flink-1.4.0

echo "s3.access-key: $S3_ACCESS_KEY" >> $FLINK_HOME/conf/flink-conf.yaml
echo "s3.secret-key: $S3_SECRET_KEY" >> $FLINK_HOME/conf/flink-conf.yaml
echo "s3.endpoint: $S3_ENDPOINT" >> $FLINK_HOME/conf/flink-conf.yaml
