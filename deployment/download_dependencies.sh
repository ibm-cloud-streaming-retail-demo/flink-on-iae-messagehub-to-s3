#!/bin/bash

set -e
set -u

DESTDIR=/home/wce/clsadmin/flink-on-iae-messagehub-to-s3/deployment/flink-1.4.0/lib/
TMPDIR=$(mktemp -d)

if [ ! -d apache-ivy-2.4.0 ]; then
   wget -q -c http://apache.mirror.anlx.net//ant/ivy/2.4.0/apache-ivy-2.4.0-bin.zip
   unzip apache-ivy-2.4.0-bin.zip
fi

cat << EOF > ivysettings.xml
<ivysettings>
    <settings defaultResolver="chain"/>
    <caches  defaultCacheDir="${TMPDIR}" />
    <resolvers>
        <chain name="chain">
            <ibiblio name="central" m2compatible="true"/>
        </chain>
    </resolvers>
</ivysettings>
EOF

java -jar apache-ivy-2.4.0/ivy-2.4.0.jar -settings ivysettings.xml -notransitive -dependency org.apache.hadoop hadoop-aws 2.7.3
java -jar apache-ivy-2.4.0/ivy-2.4.0.jar -settings ivysettings.xml -notransitive -dependency com.amazonaws aws-java-sdk-s3 1.11.183
java -jar apache-ivy-2.4.0/ivy-2.4.0.jar -settings ivysettings.xml -notransitive -dependency com.amazonaws aws-java-sdk-core 1.11.183
java -jar apache-ivy-2.4.0/ivy-2.4.0.jar -settings ivysettings.xml -notransitive -dependency com.amazonaws aws-java-sdk-kms 1.11.183
java -jar apache-ivy-2.4.0/ivy-2.4.0.jar -settings ivysettings.xml -notransitive -dependency com.fasterxml.jackson.core jackson-annotations 2.6.7
java -jar apache-ivy-2.4.0/ivy-2.4.0.jar -settings ivysettings.xml -notransitive -dependency com.fasterxml.jackson.core jackson-core 2.6.7
java -jar apache-ivy-2.4.0/ivy-2.4.0.jar -settings ivysettings.xml -notransitive -dependency com.fasterxml.jackson.core jackson-databind 2.6.7
java -jar apache-ivy-2.4.0/ivy-2.4.0.jar -settings ivysettings.xml -notransitive -dependency joda-time joda-time 2.8.1
java -jar apache-ivy-2.4.0/ivy-2.4.0.jar -settings ivysettings.xml -notransitive -dependency org.apache.httpcomponents httpcore 4.4.4
java -jar apache-ivy-2.4.0/ivy-2.4.0.jar -settings ivysettings.xml -notransitive -dependency org.apache.httpcomponents httpclient 4.5.3

find $TMPDIR -name *-javadoc.jar | xargs rm
find $TMPDIR -name *-sources.jar | xargs rm
find $TMPDIR -name *.jar | xargs cp -t $DESTDIR

rm -rf $TMPDIR
