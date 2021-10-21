FROM openjdk:latest
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY env.sh /env.sh

ENTRYPOINT ["/bin/bash" , "-c", "source /env.sh && printenv && java -jar /app.jar"]
