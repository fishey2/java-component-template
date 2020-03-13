# Java Component Template 

[![Actions Status](https://github.com/fishey2/java-component-template/workflows/Java%20CI/badge.svg)](https://github.com/fishey2/java-component-template/actions)
[![codecov](https://codecov.io/gh/fishey2/java-component-template/branch/master/graph/badge.svg?token=BuPjnBJ5YK)](https://codecov.io/gh/fishey2/java-component-template)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fishey2_java-component-template&metric=alert_status)](https://sonarcloud.io/dashboard?id=fishey2_java-component-template)

## Description

Component template project for Java including Spring Boot 2 and JUnit

[Documentation](https://fishey2.github.io/java-component-template) for this, including testing Spring Boot 2 with JUnit5 and Mockito.

## Dependencies

The following dependencies are used within this project,

| Dependency               | Version        | Usage                                              | Licence            |
|--------------------------|----------------|:---------------------------------------------------|--------------------|
| spring-boot-dependencies | v2.2.2.RELEASE | Dependencies for Spring Boot 2                     | Apache 2.0         |
| spring-boot-starter-web  | v2.0.5.RELEASE | Spring Boot Web Starter for Configuring Spring App | Apache 2.0         |
| logback-classic          | v1.2.3         | Logging framework                                  | EPL 1.0 & LGPL 2.1 |
| springfox-swagger2       | v2.9.2         | API Auto Documentation                             | Apache 2.0         |
| springfox-swagger-ui     | v2.9.2         | Serves the Swagger Documentation on the Spring App | Apache 2.0         |

### Test Dependencies

| Dependency               | Version        | Usage                                                 | Licence    |
|--------------------------|----------------|-------------------------------------------------------|------------|
| junit-jupiter            | v5.5.2         | Testing Framework                                     | EPL 2.0    |
| spring-boot-starter-test | v2.0.5.RELEASE | Contains MockMVC for testing Spring Boot applications | Apache 2.0 |
| mockito-core             | v2.22.0        | Java test mocking libraries                           | MIT        |
| rest-assured             | v4.1.2         | API Test Framework                                    | Apache 2.0 |
| json-path                | v4.1.2         | JsonPath Implementation                               | Apache 2.0 |
| xml-path                 | v4.1.2         | XmlPath Implementation                                | Apache 2.0 |
| 
### Plugins

| Plugin                          | Version       | Usage                               |
|---------------------------------|---------------|:------------------------------------|
| java                            | -             | Gradle Java Plugin                  | 
| idea                            | -             | Gradle IntelliJ Plugin              |
| jacoco                          | -             | Code coverage plugin                |
| org.springframework.boot        | 2.0.5.RELEASE | Spring Boot plugin                  | 
| io.spring.dependency-management | 1.0.7.RELEASE | Spring Dependency Management plugin |
| com.avast.gradle.docker-compose | 0.10.7        | Docker Compose Gradle plugin        |
| org.sonarqube                   | v2.8          | SonarQube plugin                    | 

### Integration

| Integration                                                                       | Usage                               |
|----------------------------------------------------------------------------------|------------------------------------|
| [GitHub Actions](https://github.com/fishey2/java-component-template/actions)     | Continuous Integration Tool        |
| [SonarCloud](https://sonarcloud.io/dashboard?id=fishey2_java-component-template) | Code Quality and Security Analysis 
| [CodeCov](https://codecov.io/gh/fishey2/java-component-template)                 | Test Coverage and Analysis         |
 
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
