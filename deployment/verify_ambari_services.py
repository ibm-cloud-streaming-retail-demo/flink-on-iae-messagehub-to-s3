#!/usr/bin/env python

import os
import sys
import json
import requests
import time
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

all_required_services_are_started = False

while not all_required_services_are_started:
    if datetime.now() > max_time:
       println('Unable to verify all services started within 30 minutes')
       sys.exit(-1)

    all_required_services_are_started = True

    print('Getting service status')
    response = requests.get( url, auth=auth )
    response.raise_for_status()

    for svc in response.json()['items']:
        response = requests.get( svc['href'], auth=auth )
        response.raise_for_status()
        j = response.json()['ServiceInfo']

        service_name = j['service_name']
        state = j['state']

        if service_name not in ['SQOOP', 'SLIDER', 'TEZ', 'PIG']:
            print(str(datetime.now()) + ' ' + service_name + ' ' + state)
            if state != 'STARTED':
	        all_required_services_are_started = False

    print('sleeping 30 seconds')
    time.sleep(30)

print('All services started as expected')
sys.exit(0) # success!
