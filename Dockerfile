FROM openjdk:21-jdk
LABEL authors="Michael & Alex"
COPY target/StarTaskz-0.0.1-SNAPSHOT.jar .
ENV PORT=$PORT

ENTRYPOINT ["java", "-jar", "StarTaskz-0.0.1-SNAPSHOT.jar"]