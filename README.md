# todo-list-demo
A full-stack CRUD demo application built with **Spring Boot**, **Postgres**, **React (Vite)**, and **Docker Compose**.

## Setup and Quickstart
### Install System Dependencies
Make sure you have the following dependencies installed and available on your system. Note that Docker is the primary way to develop against the codebase:
* [Git](https://git-scm.com/): any recent version.
* [Docker](https://www.docker.com/): any recent version.
	* [Windows Subsystem for Linux (WSL)](https://learn.microsoft.com/en-us/windows/wsl/) (if running Windows): any recent version.

### Optional Dependencies (if developing on local machine outside of Docker)
* [Java](https://adoptium.net/): version 17 or higher.
* [Node.js](https://nodejs.org/): version 20 or higher.
* [npm](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm): any recent version.

### Clone the Repo
With Git:

```shell
git clone https://github.com/leibylucw/todo-list-demo.git
cd todo-list-demo
```

### Spin Up with Docker
The repository includes a `compose.yml` that orchestrates the full stack. To build and run everything:

```shell
docker compose up -d
```

Services will be available at:
* Frontend: [http://localhost:5173](http://localhost:5173)
* Backend API: [http://localhost:8080/api](http://localhost:8080/api)
* Postgres: port `5432` (credentials in `.env`, see below)

To stop services:

```shell
docker compose down
```

(Add an optional `-v` flag to tear down the network and associated volumes in case of container/data corruption).

## Development
This repository is structured as a standard frontend + backend monorepo.

* **Backend**: Spring Boot (Java 17, Gradle), exposing RESTful APIs for `users` and `tasks`.  
* **Database**: Postgres 16, defined as a Docker service.  
* **Frontend**: React (Vite + TypeScript) SPA, consuming backend APIs.  

### A Note About Secrets
Database credentials and service ports are defined in `.env`. First, copy `.env-sample` to `.env` and adjust values as needed.

### With System-Wide Tooling
#### Backend
From the `backend/` directory:

```shell
./gradlew bootRun
```

By default, the backend will start on port `8080`.

#### Frontend
From the `frontend/` directory:

```shell
npm install
npm run dev
```

By default, the frontend will start on port `5173`.



### With Docker
Because production runs in Docker, you can also develop the app locally the same way. This means you don’t need Java or Node installed on your system. The provided `docker-compose.yml` builds both services and wires them up.

```shell
docker compose up -d
```

View logs:

```shell
docker compose logs -f backend
docker compose logs -f frontend
```

Rebuild if needed:

```shell
docker compose build --no-cache
```



## API
The backend exposes REST endpoints under `/api`.

### Users
* `GET /api/users` → list users
* `POST /api/users` → create a user
* `PATCH /api/users/{id}` → update a user
* `DELETE /api/users/{id}` → delete a user

### Tasks
* `GET /api/tasks` → list tasks
* `GET /api/users/{id}/tasks` → list tasks for a given user
* `POST /api/tasks` → create a task
* `PATCH /api/tasks/{id}` → update a task
* `DELETE /api/tasks/{id}` → delete a task



## Project Structure
```
backend/        # Spring Boot app (controllers, services, repos, entities, DTOs)
frontend/       # React + Vite SPA
docker-compose.yml
.env
```



## Future Steps
This project is intended as a **toy/demo example** — enough to demonstrate a working full-stack CRUD application, but not yet production-ready.  
The following are natural next steps for evolving it into a real-world application.

### Backend Improvements
* Add authentication and authorization (Spring Security + JWT).
* Implement validation and better error handling.
* Add unit and integration tests (JUnit, Mockito).
* Introduce caching (e.g., Redis) for performance.

### Frontend Improvements
* Implement user authentication flow (login/logout, protected routes).
* Add form validation and user-friendly error messages.
* Expand UI beyond CRUD — filtering, sorting, pagination.

### DevOps and Deployment
* Containerize frontend for production with nginx.
* Add CI/CD pipeline (GitHub Actions).
* Deploy to cloud (AWS ECS, GCP Cloud Run, or Fly.io).
* Manage secrets securely (environment variables, cloud secrets manager).

### Architecture and Scalability
* Migrate to a microservices architecture if scale demands.
* Add API versioning and documentation (Swagger/OpenAPI).
* Introduce message queues (e.g., RabbitMQ, Kafka) for async workflows.
* Harden database with migrations, backups, and monitoring.



## Deployment
At present, deployment is not configured. Future deployment steps could include:
* Building production frontend with `npm run build` and serving with nginx
* Packaging backend as a Docker image and pushing to a registry
* Deploying via Fly.io, AWS ECS, or similar
* Managing secrets with environment variables



## Author
Built as an interview-prep demo project to showcase:
* A modular Spring Boot backend with DTOs, services, and repositories
* A React SPA consuming REST APIs
* A Postgres database managed via Docker
* A fully containerized, reproducible setup
