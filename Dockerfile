# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the jar file into the container at /app
COPY target/microservices-0.0.1-SNAPSHOT.war /app/microservices-0.0.1.war

# Run the jar file with a specific profile
ENTRYPOINT ["java", "-jar", "/app/microservices-0.0.1.war", "--spring.profiles.active=docker"]

