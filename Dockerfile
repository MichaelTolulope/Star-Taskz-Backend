FROM openjdk:21-jdk
LABEL authors="Michael"
ARG JAR_FILE=target/*.jar
COPY ./target/StarTaskz-0.0.1-SNAPSHOT.jar StarTaskz-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/StarTaskz-0.0.1-SNAPSHOT.jar"]
