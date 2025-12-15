FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /build

# Build and install common event library
COPY common-event-library /common-event-library
WORKDIR /common-event-library
RUN mvn clean install -DskipTests

# Build inventory service
WORKDIR /build
COPY inventory-service/pom.xml .
COPY inventory-service/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /build/target/*.jar inventory-service.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "inventory-service.jar"]
