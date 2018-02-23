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

curl -v --user $IAE_USERNAME:$IAE_PASSWORD -H "X-Requested-By: ambari" -i -X POST \
     -d '{ "RequestInfo": {"command": "RESTART","context": "Restart all required services","operation_level": "host_component"}, "Requests/resource_filters": [{"hosts_predicate": "HostRoles/stale_configs=true" }] }' \
     https://$IAE_AMBARI_HOSTNAME:$IAE_AMBARI_PORT/api/v1/clusters/$CLUSTER_NAME/requests

python ./verify_ambari_services.py \
	$IAE_AMBARI_HOSTNAME \
	$IAE_AMBARI_PORT \
	$IAE_USERNAME \
	$IAE_PASSWORD \
	$CLUSTER_NAME
