
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

    mvn clean install -Pbuild-jar
    
    # Upload application jar to server
    scp ./target/messagehub-to-s3-1.0-SNAPSHOT.jar clsadmin@your-cluster-hostname:/home/clsadmin/

### Cluster steps

    ssh clsadmin@$your-cluster-hostname
    
    git clone https://github.com/ibm-cloud-streaming-retail-demo/flink-on-iae-messagehub-to-s3
    cd deployment
    
    cp credentials.sh_template /home/clsadmin/credentials.sh
    
    # edit /home/clsadmin/credentials.sh
   
    ./set_ambari_properties
    
    # wait for script to finish - this could take 20 minutes or more

    # Deploy Flink job - yarn single job

    source /home/clsadmin/credentials.sh

    export HADOOP_CONF_DIR=/etc/hadoop/conf

    ${FLINK_HOME}/bin/flink run -m yarn-cluster -yn 2 -yd /home/clsadmin/messagehub-to-s3-1.0-SNAPSHOT.jar \
      --kafka-brokers ${KAFKA_BROKERS} \
      --kafka-topic ${KAFKA_TOPIC} \
      --kafka-username ${KAFKA_USERNAME} \
      --kafka-password ${KAFKA_PASSWORD} \
      --kafka-group-id ${KAFKA_GROUP_ID} \
      --output-folder s3://${S3_BUCKET}/${S3_FOLDER} \
      --output-bucket-format-string ${S3_BUCKET_FORMAT_STRING}

    # Verify the output
    hadoop fs -ls cos://${S3_BUCKET}.${S3_SERVICENAME}/${S3_FOLDER}
    
