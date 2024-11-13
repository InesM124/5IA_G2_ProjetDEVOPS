# Use OpenJDK base image
FROM openjdk:11-jdk-slim as base

# Expose the application's port
EXPOSE 8083

# Copy the JAR file to the Docker container
ADD target/DevOps_Project-1.0.jar DevOps_Project-1.0.jar

# Run the JAR file when the container starts
ENTRYPOINT ["java", "-jar", "/DevOps_Project-1.0.jar"]
