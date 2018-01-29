
### Introduction

Run the Flink Word Count example on IBM Analytics Engine as a yarn application.  

In this example, Flink reads a license from COS S3 performs a word count and saves the output to COS S3.

IAE sets up S3 to be accessed with a cos:// prefix whereas Flink requires a s3:// prefix, so in this example we have to configure both endpoints.

### Prerequisites

These instructions assume that you have a IBM COS S3 endpoint configured with [HMAC authentication](https://console.bluemix.net/docs/services/cloud-object-storage/iam/service-credentials.html#service-credentials), i.e.

Use the following steps to create a service credential:

 - Log in to the IBM Cloud console and navigate to your instance of Object Storage.
 - In the side navigation, click Service Credentials.
 - Click New credential and provide the necessary information. If you want to generate HMAC credentials, specify the following in the Add Inline Configuration Parameters (Optional) field: `{"HMAC":true}`
 - Click Add to generate service credential.
 - Open the new service credentials and find the attributes:
 
```
"cos_hmac_keys": {
  "access_key_id": "<Access Key ID>",
  "secret_access_key": "<Secret Access Key>"
},
```
 - The endpoint can be found by going to the side navigation, click Endpoint
 - select the **private** endpoint for your location.

### Configure IAE for COS

These instructions are taken from the [IAE docs](https://console.bluemix.net/docs/services/AnalyticsEngine/configure-COS-S3-object-storage.html#configuring-clusters-to-work-with-ibm-cos-s3-object-stores).

 - Open the Ambari console, and then the advanced configuration for HDFS.
 - Ambari dashboard > HDFS > Configs > Advanced > Custom core-site > Add Property
 - Add the properties and values.
 
 Note that the value for <servicename> can be any literal such as `myobjectstore`.

```
fs.cos.<servicename>.access.key=<Access Key ID>
fs.cos.<servicename>.secret.key=<Secret Access Key>
fs.cos.<servicename>.endpoint=<EndPoint URL>

# Also, in addition to the IAE documentation instructions, we need to set up to access S3 urls which are used by Flink

fs.s3a.access.key=<Access Key ID>
fs.s3a.secret.key=<Secret Access Key>
fs.s3a.endpoint=<EndPoint URL>

# Finally, Flink requires the fs.s3.impl variable to be set:

fs.s3.impl=org.apache.hadoop.fs.s3a.S3AFileSystem
```

 - Save your changes and restart any affected services. The cluster will have access to your object store.
 
### Build the application jar file

    mvn clean install -Pbuild-jar
    
### Upload application jar to server

    scp ./target/messagehub-to-s3-1.0-SNAPSHOT.jar clsadmin@your-cluster-name:/home/clsadmin/

### Open SSH session

    ssh clsadmin@your-cluster-name
    
### Set variables

    # Create a file vars.sh

    ############################
    ### Set custom variables ###
    ############################

    export S3_ACCESS_KEY=your-access-key
    export S3_SECRET_KEY=your-secret-key
    export S3_ENDPOINT=your-endpoint
    export S3_BUCKET=your-bucket
    export S3_FOLDER=your-folder
    
    export S3_BUCKET_FORMAT_STRING="yyyy-MM-dd--HHmm"
    
    # your-servicename as configured in IAE Ambari
    export S3_SERVICENAME=your-servicename
    
    # Format host1:port1,host2:port2,...,hostN:portN
    export KAFKA_BROKERS=your-brokers
    
    export KAFKA_TOPIC=transactions_load
    export KAFKA_USERNAME=your-kafka-username
    export KAFKA_PASSWORD=your-kafka-password
    export KAFKA_GROUP_ID=kafka-flink-iae-streaming-demo
    
    export FLINK_HOME=flink-1.4.0
    export FLINK_LIB=$FLINK_HOME/lib/
    export FLINK_CONF=$FLINK_HOME/conf/flink-conf.yaml

### Install Flink

    #####################
    ### Install Flink ###
    #####################
    
    source vars.sh

    # Download Flink - IAE as of 2018-01-29 is based on hadoop 2.7
    wget -c -O flink-1.4.0-hadoop27-scala_2.11.tgz \
      "http://www.apache.org/dyn/mirrors/mirrors.cgi?action=download&filename=flink/flink-1.4.0/flink-1.4.0-bin-hadoop27-scala_2.11.tgz"

    # Backup old flink installations
    [[ -d flink-1.4.0 ]] && mv flink-1.4.0 flink-1.4.0-backup-$(date +%Y%m%d%H%M%S)
    
    # Extract clean installation
    tar xf flink-1.4.0-hadoop27-scala_2.11.tgz
  
    ######################
    ### Setup Flink S3 ###
    ######################
    
    # For more information, see: https://ci.apache.org/projects/flink/flink-docs-release-1.4/ops/deployment/aws.html
    
    rm -f flink-1.4.0/lib/flink-shaded-hadoop2-uber-1.4.0.jar
    
    ### Start Flink session

    # For more information, see: https://ci.apache.org/projects/flink/flink-docs-release-1.4/ops/deployment/yarn_setup.html

    # Run a Flink session
    # TODO how to determine what values to set for the arguments?
    #      see https://ci.apache.org/projects/flink/flink-docs-release-1.4/ops/deployment/yarn_setup.html#start-a-session)
    
    export HADOOP_CONF_DIR=/etc/hadoop/conf
   
    ${FLINK_HOME}/bin/yarn-session.sh -d -n 4

    # View the Flink session running on yarn
    yarn application -list

### Deploy Flink job - yarn session

    source vars.sh

    export HADOOP_CONF_DIR=/etc/hadoop/conf

    # For more information, on running flink on yarn, 
    # see: https://ci.apache.org/projects/flink/flink-docs-release-1.4/ops/deployment/yarn_setup.html
       
    ${FLINK_HOME}/bin/flink run /home/clsadmin/messagehub-to-s3-1.0-SNAPSHOT.jar \
      --kafka-brokers ${KAFKA_BROKERS} \
      --kafka-topic ${KAFKA_TOPIC} \
      --kafka-username ${KAFKA_USERNAME} \
      --kafka-password ${KAFKA_PASSWORD} \
      --kafka-group-id ${KAFKA_GROUP_ID} \
      --output-folder s3://${S3_BUCKET}/${S3_FOLDER} \
      --output-bucket-format-string ${S3_BUCKET_FORMAT_STRING}

    # Verify the output
    hadoop fs -ls cos://${S3_BUCKET}.${S3_SERVICENAME}/${S3_FOLDER}

### Deploy Flink job - yarn single job

    source vars.sh

    export HADOOP_CONF_DIR=/etc/hadoop/conf

    ${FLINK_HOME}/bin/flink run -m yarn-cluster -yn 2 /home/clsadmin/messagehub-to-s3-1.0-SNAPSHOT.jar \
      --kafka-brokers ${KAFKA_BROKERS} \
      --kafka-topic ${KAFKA_TOPIC} \
      --kafka-username ${KAFKA_USERNAME} \
      --kafka-password ${KAFKA_PASSWORD} \
      --kafka-group-id ${KAFKA_GROUP_ID} \
      --output-folder s3://${S3_BUCKET}/${S3_FOLDER} \
      --output-bucket-format-string ${S3_BUCKET_FORMAT_STRING}

    # Verify the output
    hadoop fs -ls cos://${S3_BUCKET}.${S3_SERVICENAME}/${S3_FOLDER}
    
    
### Yarn session or cluster?

Should we deploy as flink session or standalone with yarn-cluster? What are the differences?
