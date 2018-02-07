#!/usr/bin/bash

# Quit on errors
set -e

source /home/clsadmin/credentials.sh

# Throw error if undefined variable encountered
set -u

IAE_AMBARI_PORT=9443

function set_key {

  KEY=$1
  VAL=$2

  /var/lib/ambari-server/resources/scripts/configs.py \
     -a set \
     -l ${IAE_AMBARI_HOSTNAME} -t ${IAE_AMBARI_PORT} -s https \
     -n AnalyticsEngine -c core-site \
     -u ${IAE_USERNAME} -p ${IAE_PASSWORD} \
     -k $KEY \
     -v $VAL                  
 }  

set_key "fs.cos.${S3_SERVICENAME}.access.key" "${S3_ACCESS_KEY}"
set_key "fs.cos.${S3_SERVICENAME}.secret.key" "${S3_SECRET_KEY}"
set_key "fs.cos.${S3_SERVICENAME}.endpoint"   "${S3_ENDPOINT}"
set_key "fs.s3a.access.key"                   "${S3_ACCESS_KEY}"
set_key "fs.s3a.secret.key"                   "${S3_SECRET_KEY}"
set_key "fs.s3a.endpoint"                     "${S3_ENDPOINT}"
set_key "fs.s3.impl" "org.apache.hadoop.fs.s3a.S3AFileSystem"

CLUSTER_NAME=AnalyticsEngine

curl -k -v --user $IAE_USERNAME:$IAE_PASSWORD -H "X-Requested-By: ambari" -i -X PUT -d '{"RequestInfo": {"context": "Stop All Services via REST"}, "ServiceInfo": {"state":"INSTALLED"}}' https://$IAE_AMBARI_HOSTNAME:$IAE_AMBARI_PORT/api/v1/clusters/$CLUSTER_NAME/services

    # FIXME: enable more sophisticated checks for service status
    sleep 200

curl -k -v --user $IAE_USERNAME:$IAE_PASSWORD -H "X-Requested-By: ambari" -i -X PUT -d '{"RequestInfo": {"context": "Start All Services via REST"}, "ServiceInfo":{"state":"STARTED"}}' https://$IAE_AMBARI_HOSTNAME:$IAE_AMBARI_PORT/api/v1/clusters/$CLUSTER_NAME/services

    # FIXME: enable more sophisticated checks for service status
    sleep 700


python ./verify_ambari_services.py \
	$IAE_AMBARI_HOSTNAME \
	$IAE_AMBARI_PORT \
	$IAE_USERNAME \
	$IAE_PASSWORD \
	$CLUSTER_NAME
