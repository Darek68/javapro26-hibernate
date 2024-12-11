FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY target/Application-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","Application-0.0.1-SNAPSHOT.jar"]
