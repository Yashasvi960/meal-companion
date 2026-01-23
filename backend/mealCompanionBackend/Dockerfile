FROM eclipse-temurin:17-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /mealCompanionBackend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/mealCompanionBackend-0.0.1-SNAPSHOT.jar"]
