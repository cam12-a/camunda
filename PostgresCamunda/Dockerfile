FROM openjdk:latest
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV URLTOAPI=${URLTOAPI}

ENTRYPOINT ["java","-jar","/app.jar"]

