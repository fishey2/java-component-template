# Java Component Template 

[![Actions Status](https://github.com/fishey2/footprint-backend/workflows/Java%20CI/badge.svg)](https://github.com/fishey2/footprint-backend/actions)
[![codecov](https://codecov.io/gh/fishey2/footprint-backend/branch/master/graph/badge.svg?token=P8Z80pYCt0)](https://codecov.io/gh/fishey2/footprint-backend)

## Description
A recipe/shopping based carbon footprint minimiser

## Installed

- Swagger
- Spring Boot 2
- Logback (logging)
- JUnit5 (Mockito, Spring)

## Docker

```bash

# Building the docker container
(projectRoot)$ docker-compose build

-> This will generate a container roboautomator/foodprint:0.1-SNAPSHOT

# Running the docker container
(projectRoot)$ docker-compose up &

-> This will launch the service on port 8080

# Stopping the docker container
(projectRoot)$ docker-compose down
```