# Automated Greenhouse Management System (AGMS)
### Microservices Platform for Precision Agriculture

## Project Overview
The **Automated Greenhouse Management System (AGMS)** is a  microservice-based application designed for high-precision agricultural monitoring. It connects to a **sample External IoT Data Provider** to fetch real-time environmental telemetry (Temperature and Humidity) The system utilizes a custom rule engine to process this data and trigger automated actions—such as activating fans or heaters—to maintain ideal growing conditions

## Architecture & Technology Stack
The system is built on the **Spring Cloud ecosystem** to manage a distributed environment
***Infrastructure Services:** Service Discovery (Eureka), API Gateway (Spring Cloud Gateway), and Centralized Configuration (Spring Cloud Config).
* **Domain Microservices:** Zone Management, Sensor Telemetry, Automation & Control, and Crop Inventory.
* **Communication:** Synchronous inter-service communication via **OpenFeign**.
* **External Integration:** Powered by the [External IoT Backend](https://github.com/shamodhas/iot-backend.git) that was provided by My teacher

## Getting Started & Startup Sequence
To ensure service discovery and centralized configuration are handled correctly, you **must** start the services in the following order.

### 1. Service Registry (Eureka)
* **Port:** `8761` 
* **Action:** Run `EurekaServerApplication`.
* **Dashboard:** Monitor registered services at `http://localhost:8761`.

### 2. Config Server
* **Port:** `8888`
* **Action:** Run `ConfigServerApplication`. This serves centralized properties to all domain services.

### 3. API Gateway (Security Perimeter)
* **Port:** `8090` 
* **Action:** Run `ApiGatewayApplication`.
* **Note:** This service implements **JWT validation**
All external calls must include a valid Bearer Token.

### 4. Domain Microservices
Start these once the infrastructure services are "UP"
* **Zone Service:** Port `8081`
* **Sensor Service:** Port `8082` 
* **Automation Service:** Port `8083`
* **Crop Service:** Port `8084` <br> <br>

<img width="902" height="368" alt="Screenshot 2026-05-15 125151" src="https://github.com/user-attachments/assets/3bd04ea5-d3f6-477b-8dd1-145389355712" />





## Configuration Details

### Database Setup
Update your `application.properties` (or the centralized files in your `config-repo`) with your specific MySQL credentials :


# Database Configuration - for Zone and Automation services

spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
