# Java Component Template 

[![Actions Status](https://github.com/fishey2/java-component-template/workflows/Java%20CI/badge.svg)](https://github.com/fishey2/java-component-template/actions)
[![codecov](https://codecov.io/gh/fishey2/java-component-template/branch/master/graph/badge.svg?token=BuPjnBJ5YK)](https://codecov.io/gh/fishey2/java-component-template)

## Description
Component template project for Java including Spring Boot 2 and JUnit

## Installed

- Swagger
- Spring Boot 2
- Logback (logging)
- JUnit5 (Mockito, Spring)

## Docker

```bash

# Compile first
./gradlew clean build

# Building the docker container
(projectRoot)$ docker-compose build

-> This will generate a container docker.pkg.github.com/fishey2/java-component-template/component:0.1-SNAPSHOT

# Running the docker container
(projectRoot)$ docker-compose up &

-> This will launch the service on port 8080

# Stopping the docker container
(projectRoot)$ docker-compose down
```
