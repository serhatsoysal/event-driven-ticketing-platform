FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /build

# Copy common event library first
COPY common-event-library /common-event-library
WORKDIR /common-event-library
RUN mvn clean install -DskipTests

# Build saga orchestrator service
WORKDIR /build
COPY saga-orchestrator-service/pom.xml .
COPY saga-orchestrator-service/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /build/target/*.jar saga-orchestrator-service.jar

EXPOSE 8086

ENTRYPOINT ["java", "-jar", "saga-orchestrator-service.jar"]
