# Automated Greenhouse Management System (AGMS)
### 🌿 Cloud-Native Microservices Platform for Precision Agriculture

## 📌 Project Overview
[cite_start]The **Automated Greenhouse Management System (AGMS)** is a cloud-native, microservice-based application designed for high-precision agricultural monitoring[cite: 4, 21]. [cite_start]It connects to a **Live External IoT Data Provider** to fetch real-time environmental telemetry (Temperature and Humidity)[cite: 22, 23]. [cite_start]The system utilizes a custom rule engine to process this data and trigger automated actions—such as activating fans or heaters—to maintain ideal growing conditions[cite: 23, 30].

## 🏗️ Architecture & Technology Stack
[cite_start]The system is built on the **Spring Cloud ecosystem** to manage a distributed environment[cite: 9, 33]:
* [cite_start]**Infrastructure Services:** Service Discovery (Eureka), API Gateway (Spring Cloud Gateway), and Centralized Configuration (Spring Cloud Config)[cite: 115, 116, 117, 118].
* [cite_start]**Domain Microservices:** Zone Management, Sensor Telemetry, Automation & Control, and Crop Inventory[cite: 119, 120, 130, 137, 151].
* [cite_start]**Communication:** Synchronous inter-service communication via **OpenFeign**[cite: 13, 38].
* **External Integration:** Powered by the [External IoT Backend](https://github.com/shamodhas/iot-backend.git).

## 🚀 Getting Started & Startup Sequence
[cite_start]To ensure service discovery and centralized configuration are handled correctly, you **must** start the services in the following order[cite: 187]:

### 1. Service Registry (Eureka)
* [cite_start]**Port:** `8761` [cite: 116]
* **Action:** Run `EurekaServerApplication`.
* [cite_start]**Dashboard:** Monitor registered services at `http://localhost:8761`[cite: 189].

### 2. Config Server
* [cite_start]**Port:** `8888` [cite: 174]
* **Action:** Run `ConfigServerApplication`. [cite_start]This serves centralized properties to all domain services[cite: 176].

### 3. API Gateway (Security Perimeter)
* [cite_start]**Port:** `8090` [cite: 117]
* **Action:** Run `ApiGatewayApplication`.
* [cite_start]**Note:** This service implements **JWT validation**[cite: 12]. [cite_start]All external calls must include a valid Bearer Token.

### 4. Domain Microservices
[cite_start]Start these once the infrastructure services are "UP"[cite: 187, 189]:
* [cite_start]**Zone Service:** Port `8081` [cite: 120]
* [cite_start]**Sensor Service:** Port `8082` [cite: 130]
* [cite_start]**Automation Service:** Port `8083` [cite: 137]
* [cite_start]**Crop Service:** Port `8084` [cite: 151]

## 🛠️ Configuration Details

### Database Setup
[cite_start]Update your `application.properties` (or the centralized files in your `config-repo`) with your specific MySQL credentials[cite: 173]:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/AGMS?createDatabaseIfNotExist=true
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate Properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
