# Use an OpenJDK image
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the project files
COPY . .

# Build the project
RUN ./gradlew build -x test

# Expose the port
EXPOSE 9090

# Run the application
CMD ["java", "-jar", "build/libs/bank-account-api-0.0.1-SNAPSHOT.jar"]
