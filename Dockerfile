FROM java:8

ADD https://s3-eu-west-1.amazonaws.com/softwaremill-public/elasticmq-server-0.13.2.jar /
COPY queue.conf /
ENTRYPOINT ["/usr/bin/java", "-Dconfig.file=queue.conf", "-jar", "/elasticmq-server-0.13.2.jar"]

EXPOSE 9324