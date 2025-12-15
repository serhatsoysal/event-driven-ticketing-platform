FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /build

# Build and install common event library
COPY common-event-library /common-event-library
WORKDIR /common-event-library
RUN mvn clean install -DskipTests

# Build ticket service
WORKDIR /build
COPY ticket-service/pom.xml .
COPY ticket-service/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /build/target/*.jar ticket-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "ticket-service.jar"]
