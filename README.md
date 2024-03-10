# Telemetry Management Service

The Telemetry Management Service enables users to import and save telemetry data through  CSV files
and filter trough this data.

## Getting started

### Prerequisites
Ensure you have Java 17 and Docker installed.

To download the project,  use the following command to clone the repository:

```
git clone https://github.com/silvijabaro/telemetry-management.git
```

Navigate to the project root folder and start the application using the following command:
```
./gradlew
```


### Local development

To start  PostgreSQL database run:

```
docker compose -f src/main/docker/postgresql.yml up -d
```

To stop it and remove the container, run:

```
docker compose -f src/main/docker/postgresql.yml down
```
### API documentation

Once the application is running, access Swagger through the following URL:

```
http://localhost:8080/swagger-ui.html
```

### Actuator

Gain  insights about the application using the following endpoints:

```
Health endpoint: http://localhost:8080/actuator/health
Info endpoint: http://localhost:8080/actuator/info
Metrics endpoint: http://localhost:8080/actuator/metrics
```
### Dokerize app
Build the Docker image and run the application:

```
docker build -t telemetry-management-service .
docker run -p 8080:8080 telemetry-management-service
```

### Run it all together
To seamlessly run both the application and the database, use the following command:
```
docker compose up
```
