# ==========================
# Base image
# ==========================
# Använder Eclipse Temurin JDK 17 med Alpine Linux som bas.
FROM eclipse-temurin:17-jdk-alpine

# ==========================
# Working directory
# ==========================
# Alla kommandon körs i /app-mappen inuti containern
WORKDIR /app

# ==========================
# Copy JAR file
# ==========================
# Kopierar den byggda Spring Boot JAR-filen in i containern som app.jar
COPY social-app-api-1.0-SNAPSHOT.jar app.jar

# ==========================
# Expose port
# ==========================
# Öppnar port 8080 för inkommande trafik
EXPOSE 8080

# ==========================
# Entry point
# ==========================
# Startar Spring Boot-applikationen genom att köra JAR-filen
ENTRYPOINT ["java", "-jar", "app.jar"]
