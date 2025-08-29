# Centralized Configuration with Spring Cloud Config Server and Microservices

## Portfolio Showcase
This project demonstrates a professional microservice architecture highlighting **centralized configuration management**, **Dockerized orchestration**, and **REST API development**. It showcases competencies in:

- **Centralized Configuration**: Spring Cloud Config Server in native mode serving profile-specific configurations (`prod` and `test`) for multiple microservices.
- **Dynamic Configuration Refresh**: Configuration properties can be updated at runtime using `@RefreshScope` and Spring Actuator `/refresh`.
- **Microservice Design**: Task Management (`ms-task`) and Email Service (`ms-email`) implement a layered architecture with DTOs, Services, Repositories, and Entities, along with validation and global exception handling.
- **Docker Orchestration**: All services, including PostgreSQL, are containerized and orchestrated with Docker Compose, featuring healthchecks and volume persistence.
- **Profile-based Environment Support**: Separate configurations for `prod` and `test` environments loaded dynamically from the Config Server.

---

## Architecture & Centralized Configuration

- **Config Server**
  - Serves YAML configuration files for each profile.
  - Enables runtime property refresh without restarting microservices.

- **Task Management Service (`ms-task`)**
  - CRUD operations for tasks with maximum task limits dynamically enforced from Config Server.

- **Email Service (`ms-email`)**
  - Simulates sending emails with retry counts managed centrally.
  - Supports statuses: `PENDING`, `SENT`, `FAILED`.

- **Design Patterns**
  - Layered structure: Controller → Service → Repository → Entity.
  - DTOs for request validation and response mapping.
  - Global exception handling ensures consistent API responses.

- **Dynamic Config Properties**
  - Task limit (`max.task`)
  - Email retry count (`max.retries`)
  - SMTP settings
  - Database connection parameters

---

## Task Management Endpoints (`ms-task`)

Base URL: `/task`

| Endpoint | Method | Request | Response | Description |
|----------|--------|---------|---------|-------------|
| `/task` | POST | `{ "title": "Task 1", "description": "Details" }` | `{ "taskId": 1, "tasksLeft": 99 }` | Create a new task with max limit enforced by Config Server. |
| `/task/{taskId}` | GET | - | `{ "taskId": 1, "title": "Task 1", "description": "Details", "createdAt": "2025-08-29T21:00:00" }` | Fetch a task by ID. |
| `/task/{taskId}` | PUT | `{ "title": "Updated", "description": "Updated details" }` | `"UPDATED"` | Update a task. |
| `/task/{taskId}` | DELETE | - | `"DELETED"` | Delete a task. |
| `/task` | GET | - | `[ {...}, ... ]` | List all tasks. |

---

## Email Service Endpoints (`ms-email`)

Base URL: `/email`

| Endpoint | Method | Request | Response | Description |
|----------|--------|---------|---------|-------------|
| `/email` | POST | `{ "recipient": "user@example.com", "subject": "Hello", "content": "Message body" }` | `{ "emailId": 1 }` | Send an email with retries enforced from Config Server. |
| `/email/{emailId}` | GET | - | `{ "emailId": 1, "recipient": "user@example.com", "subject": "Hello", "content": "Message body", "status": "SENT", "attemptCount": 1, "sendAt": "2025-08-29T21:00:00" }` | Fetch an email by ID. |
| `/email/{emailId}` | DELETE | - | `"DELETED FROM DATABASE"` | Delete an email. |
| `/email` | GET | - | `[ {...}, ... ]` | List all emails. |

---

## Docker Orchestration Overview

- **Services**:
  - PostgreSQL (`postgres`)
  - Config Server (`config-server`)
  - Task Service (`ms-task`)
  - Email Service (`ms-email`)

- **Features**:
  - Healthchecks ensure proper startup order.
  - Environment variables allow dynamic configuration injection.
  - Volumes persist PostgreSQL data.
  - Services exposed on dedicated ports.
````markdown

## Startup Command
```bash
docker-compose up --build
````

## Access Points

* **Task Service**: [http://localhost:8080/task](http://localhost:8080/task)
* **Email Service**: [http://localhost:8081/email](http://localhost:8081/email)
* **Config Server Health**: [http://localhost:8888/actuator/health](http://localhost:8888/actuator/health)

## Runtime Configuration Refresh

```bash
POST http://localhost:8080/actuator/refresh
POST http://localhost:8081/actuator/refresh
```

## Technical Highlights

* Spring Boot 3.5, Java 21
* Spring Cloud Config 2025.0.0
* Dockerized microservices with Compose orchestration
* PostgreSQL persistent storage
* DTO-based request/response mapping and validation
* Global exception handling with meaningful error responses
* Dynamic, profile-based configuration for multiple environments
* REST API demonstrating clean architecture and scalable service design
