# Use the official OpenJDK 17 image from the Docker Hub
FROM eclipse-temurin:17-jdk-jammy

# Set the working directory
WORKDIR /app

# Copy the JAR file from the target directory to the container
COPY target/*.jar app.jar

# Specify the entry point for the container
ENTRYPOINT ["java", "-jar", "app.jar"]
