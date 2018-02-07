
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

### Build the application jar file

    # RECOMMENDED: 
    # Copy your ssh key to the cluster this will prevent you from having to enter credentials when using ssh/scp
    ssh-copy-id clsadmin@${IAE_SSH_HOSTNAME}

    mvn clean install -Pbuild-jar
    
    # Upload application jar to server
    scp ./target/messagehub-to-s3-1.0-SNAPSHOT.jar clsadmin@${IAE_SSH_HOSTNAME}:/home/clsadmin/

### Cluster steps

    ssh clsadmin@${IAE_SSH_HOSTNAME}
    
    git clone https://github.com/ibm-cloud-streaming-retail-demo/flink-on-iae-messagehub-to-s3
    cd deployment
    
    cp credentials.sh_template /home/clsadmin/credentials.sh
    
Now edit /home/clsadmin/credentials.sh to reflect your environment
   
    # run the script to set the cos and s3 properties
    sh ./set_ambari_properties.sh
    
    # install flink
    sh ./install_flink.sh
    
    # set s3 credentials - is this required, creds are in core-site.xml
    #sh ./configure_flink.sh
    
    # download hadoop-aws jars for s3
    sh ./download_dependencies.sh
    
Wait for script to finish - this could take 20 minutes or more

    # Deploy Flink job - yarn single job
    
    FLINK_HOME=/home/wce/clsadmin/flink-on-iae-messagehub-to-s3/deployment/flink-1.4.0

    source /home/clsadmin/credentials.sh

    export HADOOP_CONF_DIR=/etc/hadoop/conf
    
    YARN_BACKGROUND=""
    #YARN_BACKGROUND="-yd"

    ${FLINK_HOME}/bin/flink run -m yarn-cluster -yn 2 $YARN_BACKGROUND /home/clsadmin/messagehub-to-s3-1.0-SNAPSHOT.jar \
      --kafka-brokers ${KAFKA_BROKERS} \
      --kafka-topic ${KAFKA_TOPIC} \
      --kafka-username ${KAFKA_USERNAME} \
      --kafka-password ${KAFKA_PASSWORD} \
      --kafka-group-id ${KAFKA_GROUP_ID} \
      --output-folder s3://${S3_BUCKET}/${S3_FOLDER} \
      --output-bucket-format-string ${S3_BUCKET_FORMAT_STRING}

    # Verify the output
    hadoop fs -ls cos://${S3_BUCKET}.${S3_SERVICENAME}/${S3_FOLDER}
    
