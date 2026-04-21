# Use Java 21
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy jar (IMPORTANT)
COPY target/*.jar app.jar

# Debug step (VERY IMPORTANT)
RUN ls -l /app

# Run app
CMD ["java", "-jar", "app.jar"]