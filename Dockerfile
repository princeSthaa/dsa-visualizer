FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY target/dsaVis-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]