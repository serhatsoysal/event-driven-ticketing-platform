# Ticket System

A microservices-based ticketing system built with Spring Boot.

## Architecture

This project consists of multiple microservices:

- **API Gateway** - Central entry point for all client requests
- **Ticket Service** - Manages ticket creation and lifecycle
- **User Service** - Handles user management and authentication
- **Payment Service** - Processes payment transactions
- **Notification Service** - Manages notifications and alerts
- **Inventory Service** - Tracks inventory and availability

## Technology Stack

- **Framework**: Spring Boot
- **Service Discovery**: Consul
- **Message Broker**: Apache Kafka
- **Database**: MySQL
- **Cache**: Redis
- **Search**: Elasticsearch
- **Monitoring**: Prometheus & Grafana
- **Containerization**: Docker & Docker Compose
- **Orchestration**: Kubernetes

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker & Docker Compose
- MySQL 8.0+

### Running with Docker Compose

```bash
docker-compose up -d
```

This will start all services and dependencies.

### Building Services

Each service can be built individually:

```bash
cd <service-name>
mvn clean install
```

## Services Ports

- API Gateway: 8080
- Ticket Service: 8081
- User Service: 8082
- Payment Service: 8083
- Notification Service: 8084
- Inventory Service: 8085
- Consul: 8500
- Kafka: 9092
- MySQL: 3306
- Redis: 6379
- Elasticsearch: 9200
- Kibana: 5601
- Prometheus: 9090
- Grafana: 3000

## License

Copyright (c) 2024

