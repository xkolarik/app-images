FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk-11.0.4_11

WORKDIR /app

COPY ../producer/target/producer-1.0-SNAPSHOT.jar producer-1.0-SNAPSHOT.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "producer-1.0-SNAPSHOT.jar"]
