# Build the runtime image in ARM64
FROM arm64v8/openjdk:17-jdk-slim

# Set the working directory inside the final runtime image
WORKDIR /app

# Copy the built JAR file from the builder image
COPY build/libs/*.jar app.jar

# Run the application using the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
