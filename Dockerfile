# Use a base image with Java 17
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build artifact (jar file) to the working directory
COPY target/pos-branch-service-0.0.1-SNAPSHOT.jar pharmacy-pos-branch-service.jar

# Expose the port the application runs on
EXPOSE 8084

# Run the jar file
ENTRYPOINT ["java", "-jar", "pharmacy-pos-branch-service.jar"]