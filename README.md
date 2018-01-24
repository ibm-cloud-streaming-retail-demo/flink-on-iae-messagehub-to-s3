[![Build Status](https://travis-ci.org/ibm-cloud-streaming-retail-demo/flink-on-iae-messagehub-to-s3.svg?branch=master)](https://travis-ci.org/ibm-cloud-streaming-retail-demo/flink-on-iae-messagehub-to-s3)

----

## Introduction

This component retrieves a stream of events and persists them to S3 as avro.

An example event:

```
{ todo }
```

## Prerequisites

IBM COS S3 endpoint with HMAC (todo instructions)

## Building

To build this component, use:

```
mvn clean package
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
```

Edit the src/main/resources/core-site.xml to provide the S3 endpoint address.

A DateTimeBucketer is used to partition the output.

### Checkstyle

To check for checkstyle violations as part of the build, you can uncomment the checkstyle plugin in the pom.xml

## Run Standalone

First start Flink (1.4+)

```
${FLINK_HOME}/bin/start-local.sh
```

Ensure you have built flink (`mvn clean package`).  You can then run with:

```
${FLINK_HOME}/bin/flink run target/todo-1.0-SNAPSHOT.jar --todo
```

## Run in IBM Analytics Engine (IAE)

Todo

## TIPS

Avro tools are really useful for working with the generated data:
 
 - http://www.michael-noll.com/blog/2013/03/17/reading-and-writing-avro-files-from-the-command-line/
