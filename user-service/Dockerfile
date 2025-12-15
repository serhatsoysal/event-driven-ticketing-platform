FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /build

# Build and install common event library
COPY common-event-library /common-event-library
WORKDIR /common-event-library
RUN mvn clean install -DskipTests

# Build user service
WORKDIR /build
COPY user-service/pom.xml .
COPY user-service/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /build/target/*.jar user-service.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "user-service.jar"]
