# Stage 1: Build with Maven + JDK 17
FROM maven:3.9.3-eclipse-temurin-17-alpine AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Stage 2: Run with JDK 17 runtime
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/db-password-generator.jar db-password-generator.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "db-password-generator.jar"]