FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk-11.0.4_11

WORKDIR /app

COPY target/consumer-1.0.0.jar /app/consumer-1.0.0.jar

EXPOSE 8082

# Defina o ponto de entrada do container como o jar do aplicativo consumer
ENTRYPOINT ["java", "-jar", "consumer-1.0.0.jar"]
