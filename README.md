[![Build Status](https://travis-ci.org/ibm-cloud-streaming-retail-demo/flink-on-iae-messagehub-to-s3.svg?branch=master)](https://travis-ci.org/ibm-cloud-streaming-retail-demo/flink-on-iae-messagehub-to-s3)

----

## Introduction

This component retrieves a stream of events and persists them to S3 as avro.

An example event:

```
{ todo }
```

## Prerequisites

### Message Hub data simulator deployed

- https://github.com/ibm-cloud-streaming-retail-demo/kafka-producer-for-simulated-data

### IBM COS S3 endpoint with HMAC

This example uses standard S3 authentication (this is called HMAC authentication - which is not enabled by default on IBM Cloud Object Storage).  To enable HMAC authentication:

- Log in to the IBM Cloud console and navigate to your instance of Object Storage.
- In the side navigation, click Service Credentials.
- Click New credential and provide the necessary information. If you want to generate HMAC credentials, specify the following in the Add Inline Configuration Parameters (Optional) field: `{"HMAC":true}`
- Click Add to generate service credential.

In the generated service credentials, you should see the standard S3 'access key id' and 'secret access key':

```
"cos_hmac_keys": {
    "access_key_id": "XXXXX",
    "secret_access_key": "XXXXX"
  }
```

## Developing

To import this project into eclipse:

```
mvn eclipse:eclipse
```

You can run the StreamingJob from eclipse.  You need to edit the Run Configuration in Eclipse and set these arguments:

```
--kafka-brokers broker1host:broker1port,broker2host:broker2port,...
--kafka-topic transactions_load
--kafka-username secret
--kafka-password secret
--kafka-group-id flink-job-123
--output-folder s3://accessKey:secretKey@bucket/folder
--output-bucket-format-string "yyyy-MM-dd--HHmm"
```

Edit the src/main/resources/core-site.xml to provide the S3 endpoint address.

A DateTimeBucketer is used to partition the output.

### Checkstyle

To check for checkstyle violations as part of the build, you can uncomment the checkstyle plugin in the pom.xml

## Run Standalone

See [Standalone Deployment](./README_STANDALONE_DEPLOY.md)

## Run on IBM Analytics Engine (IAE)

See [IAE Deployment](./README_IAE_DEPLOY.md)

## TIPS

Avro tools are really useful for working with the generated data:
 
 - http://www.michael-noll.com/blog/2013/03/17/reading-and-writing-avro-files-from-the-command-line/
