# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the project’s jar file into the container at /app
COPY target/pos-branch-service-0.0.1-SNAPSHOT.jar /app/pos-branch-service.jar

# Expose port 8084 to the outside world
EXPOSE 8084

# Run the jar file
ENTRYPOINT ["java", "-jar", "pos-branch-service.jar"]
