FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY build/libs/usuario-api-0.0.1-SNAPSHOT.jar /app/usuario-api.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/usuario-api.jar"]