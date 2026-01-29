# Stage 1: Build the application
FROM gradle:8.5-jdk17 AS build
WORKDIR /app

# Copy the wrapper and configuration files first to cache dependencies
COPY gradlew build.gradle settings.gradle ./
COPY gradle ./gradle

# Ensure the wrapper has execution permissions
RUN chmod +x gradlew

# Download dependencies (this layer is cached unless build.gradle changes)
RUN ./gradlew dependencies --no-daemon

# Copy the source code
COPY src ./src

# Build the executable JAR
RUN ./gradlew bootJar --no-daemon

# Stage 2: Create the runtime image
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Create a non-root user for security
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

# Copy the built JAR from the build stage
# Gradle puts the JAR in build/libs/
COPY --from=build /app/build/libs/search-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your service is running on (from your logs: 8083)
EXPOSE 8083

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]