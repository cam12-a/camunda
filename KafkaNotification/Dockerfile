FROM ubuntu:latest
RUN apt-get update && apt-get install -y iputils-ping
RUN apt-get install openjdk-16-jdk -y
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY env.sh env.sh

ENTRYPOINT ["/bin/bash" , "-c", "source /env.sh && printenv && java -jar /app.jar"]
