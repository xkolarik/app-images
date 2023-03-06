# Use a imagem base do OpenJDK 11
FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk-11.0.4_11

# Defina o diretório de trabalho
WORKDIR /app

# Copie o arquivo JAR do consumer para o diretório de trabalho
COPY consumer/target/consumer-1.0.0.jar /app/consumer-1.0.0.jar

# Copie o arquivo JAR do producer para o diretório de trabalho
COPY producer/target/producer-1.0.0.jar /app/producer-1.0.0.jar

# Exponha as portas 8080 e 8082
EXPOSE 8080
EXPOSE 8082

# Defina o ponto de entrada do container como o script de inicialização
ENTRYPOINT ["cmd", "/c", "/app/start.cmd"]
