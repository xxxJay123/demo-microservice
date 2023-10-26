# Use AdoptOpenJDK as the base image
FROM openjdk:17-jdk-alpine3.14

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file into the container at /app
COPY ./target/configure-service-0.0.1-SNAPSHOT.jar /app/configure-service.jar



# Specify the command to run your application
CMD ["java", "-jar", "configure-service.jar"]