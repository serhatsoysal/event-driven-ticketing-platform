FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /build

# Build and install common event library
COPY common-event-library /common-event-library
WORKDIR /common-event-library
RUN mvn clean install -DskipTests

# Build payment service
WORKDIR /build
COPY payment-service/pom.xml .
COPY payment-service/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /build/target/*.jar payment-service.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "payment-service.jar"]
