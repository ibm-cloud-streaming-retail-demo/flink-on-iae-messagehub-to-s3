#!/usr/bin/env python

import os
import sys
import json
import requests
from datetime import datetime, timedelta

if len(sys.argv) != 6:
   print("Usage: %s AMBARI_HOSTNAME AMBARI_PORT AMBARI_USERNAME AMBARI_PASSWORD CLUSTER_NAME", sys.argv[0])
   exit(-1)
   
AMBARI_HOSTNAME = sys.argv[1]
AMBARI_PORT = sys.argv[2]
AMBARI_USERNAME = sys.argv[3]
AMBARI_PASSWORD = sys.argv[4]
CLUSTER_NAME = sys.argv[5]

url = "https://{}:{}/api/v1/clusters/{}/services".format(AMBARI_HOSTNAME, AMBARI_PORT,CLUSTER_NAME)
auth = (AMBARI_USERNAME, AMBARI_PASSWORD)

response = requests.get( url, auth=auth )
response.raise_for_status()

max_time = datetime.now() + timedelta(minutes=30)

while True:
    if datetime.now() > max_time:
       println('Unable to verify all services started within 30 minutes')
       sys.exit(-1)

    for svc in response.json()['items']:
        response = requests.get( svc['href'], auth=auth )
        response.raise_for_status()
        j = response.json()['ServiceInfo']

        service_name = j['service_name']
        state = j['state']

        if service_name not in ['SQOOP', 'SLIDER', 'TEZ', 'PIG']:
            if state != 'STARTED':
                continue

    print('All services started as expected')
    sys.exit(0) # success!
