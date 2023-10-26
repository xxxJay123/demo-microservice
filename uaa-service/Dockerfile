# Use AdoptOpenJDK as the base image
FROM openjdk:17-jdk-alpine3.14
# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file into the container at /app
COPY ./target/uaa-service-0.0.1-SNAPSHOT.jar /app/uaa-service.jar

# Expose the port the application will run on
EXPOSE 8080

# Specify the command to run your application
CMD ["java", "-jar", "uaa-service.jar"]
