FROM devexdev/8-jdk-alpine

WORKDIR /usr/xmlparser

COPY target/xmlparser-1.0-SNAPSHOT.jar /usr/xmlparser/xmlparser.jar
COPY /xmlparser.sh /usr/xmlparser/xmlparser.sh
COPY /test.xml /usr/xmlparser/test.xml


#CMD ["bash", "xmlparser.sh", "sync", "test.xml"]
#bash xmlparser.sh export test2.xml
#bash xmlparser.sh sync test.xml
