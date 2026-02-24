# Use official OpenJDK image
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy project files
COPY . .

# Build app
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Run app
CMD ["java", "-jar", "target/primetrade-api-0.0.1-SNAPSHOT.jar"]