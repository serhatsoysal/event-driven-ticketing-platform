FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /build

# Build and install common event library
COPY common-event-library /common-event-library
WORKDIR /common-event-library
RUN mvn clean install -DskipTests

# Build notification service
WORKDIR /build
COPY notification-service/pom.xml .
COPY notification-service/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /build/target/*.jar notification-service.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "notification-service.jar"]
