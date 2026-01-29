# Stage 1: Build the application
FROM gradle:8.5-jdk17 AS build
WORKDIR /app

# Copy gradle files first to leverage Docker cache for dependencies
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Download dependencies
RUN ./gradlew dependencies --no-daemon

# Copy source code and build
COPY src ./src
RUN ./gradlew bootJar --no-daemon

# Stage 2: Runtime image
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Security: Run as non-root user
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

# Copy the built JAR from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]