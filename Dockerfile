# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the jar file into the container at /app
COPY target/my-app.jar /app/my-app.jar

# Run the jar file with a specific profile
ENTRYPOINT ["java", "-jar", "/app/my-app.jar", "--spring.profiles.active=docker"]

