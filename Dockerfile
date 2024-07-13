FROM openjdk:21-jdk-slim
LABEL authors="Michael"
ARG JAR_FILE=target/*.jar
COPY ./target/StarTaskz-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
