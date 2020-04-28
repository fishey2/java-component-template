# Java Component Template 

## GitHub Actions Overall

[![Actions Status](https://github.com/fishey2/java-component-template/workflows/Java%20CI/badge.svg)](https://github.com/fishey2/java-component-template/actions)

## CodeCov Overall

[![codecov](https://codecov.io/gh/fishey2/java-component-template/branch/master/graph/badge.svg?token=BuPjnBJ5YK)](https://codecov.io/gh/fishey2/java-component-template)

## SonarCloud Overall
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fishey2_java-component-template&metric=alert_status)](https://sonarcloud.io/dashboard?id=fishey2_java-component-template)

### SonarCloud Summary

[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=fishey2_java-component-template&metric=sqale_rating)](https://sonarcloud.io/component_measures?id=fishey2_java-component-template&metric=Maintainability&view=list)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=fishey2_java-component-template&metric=reliability_rating)](https://sonarcloud.io/component_measures?id=fishey2_java-component-template&metric=Reliability&view=list)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=fishey2_java-component-template&metric=security_rating)](https://sonarcloud.io/component_measures?id=fishey2_java-component-template&metric=Security&view=list)

### SonarCloud Details

[![SonarCloud Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=fishey2_java-component-template&metric=ncloc)](https://sonarcloud.io/component_measures/metric/ncloc/list?id=fishey2_java-component-template)
[![SonarCloud Coverage](https://sonarcloud.io/api/project_badges/measure?project=fishey2_java-component-template&metric=coverage)](https://sonarcloud.io/component_measures/metric/coverage/list?id=fishey2_java-component-template)
[![SonarCloud Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=fishey2_java-component-template&metric=duplicated_lines_density)](https://sonarcloud.io/component_measures/metric/duplicated_lines_density/list?id=fishey2_java-component-template)
[![SonarCloud Bugs](https://sonarcloud.io/api/project_badges/measure?project=fishey2_java-component-template&metric=bugs)](https://sonarcloud.io/component_measures/metric/reliability_rating/list?id=fishey2_java-component-template)
[![SonarCloud Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=fishey2_java-component-template&metric=vulnerabilities)](https://sonarcloud.io/component_measures/metric/vulnerabilities/list?id=fishey2_java-component-template)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=fishey2_java-component-template&metric=code_smells)](https://sonarcloud.io/component_measures?id=fishey2_java-component-template&metric=new_code_smells&view=list)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=fishey2_java-component-template&metric=sqale_index)](https://sonarcloud.io/component_measures?id=fishey2_java-component-template&metric=new_technical_debt&view=list)


## Description

Component template project for Java including Spring Boot 2 and JUnit

The goal of this template project is to have branches that are ready to go with different non-conflicting 
implementations for various things on-top of the master branch.

```bash
// Pull in the reference implementation for ActiveMQ
git pull -m origin with_activemq

// Pull in the reference implementation for PostgreSQL
git pull -m origin with_postgresql

// Pull in the reference implementation for Flyway DB Migrations
git pull -m origin with_flyway
```

List of branches:
- with_activemq (WIP to be removed as default)
- with_kafka (WIP - do not remove)
- with_amqp (to be renamed as with_rabbitmq - do not remove)

TODO List (master):
- [ ] Audit? Check for Updates/Dependencies?
- [ ] Separate Unit and Integration tests
- [ ] Spike for Canary Updates/Build
- [ ] Pre/Post Git Hooks? Secrets?
    - [ ] Migrate from GitHub Workflow (move to branch) use CircleCI (default) instead
    - [ ] or travis CI? 
- [X] Update project badges
- [ ] Logging Correlation (Microservices)
- [ ] Logging Middleware for HttpRequests and Responses
    - [X] HttpServletRequest
- [ ] Authorisation Middleware
- [X] Update JMS implementation to use transactions instead
    - Done by default for reading and handling the inbound message
- [X] Fix issue with dockerComposeDown not working after testing
    - [ ] Writeup post of Gradle 7 and ordering
- [X] Working pipeline for branches and pull requests (GitHub)

List of desired parts:
- [ ] Build Automation
    - [X] Gradle (Default)
    - [ ] Maven
- [ ] Messaging (Can be run in parallel)
    - [X] ActiveMQ (JMS - Default)
        - [ ] Update docs with SSL
        - [ ] Update docs with transactional messaging (if appl)
    - [ ] RabbitMQ (AMQP - with_rabbitmq) - BLOCKED (no v1.0 AMQP client)
    - [ ] MQTT (with_mqtt)
    - [ ] Kafka (with_kafka)
- [ ] Databases (Only one type can be used, if you call with_mongodb it will overwrite PostgreSQL)
    - [ ] Data Views
    - [ ] PostgreSQL (Default)
        - [ ] JPA/Hibernate with Spring
        - [ ] Test Seeding/Transactions
        - [ ] Schema Management
    - [ ] MongoDB (with_mongodb)
- [ ] Swagger2 (Default)
- [ ] Middleware
    - [ ] Logging (Default) - Not Impl
- [ ] Authentication/Authorisation
    - [ ] OAuth2.0 (with_oauth)
    - [ ] JWT (with_jwt)
    - [ ] Basic (Default)
    - [ ] CSRF
- [ ] API Implementations
    - [X] RESTFul (Default)
        - [ ] Add Validation - and docs
    - [ ] GraphQL (with_graphql)
- [ ] Testing Frameworks
    - [X] Rest Assured (Default)
- [ ] CI/CD
    - [X] Git Workflow (Default)
    - [ ] Makefile (with_make)
    - [ ] Dojo (with_dojo)
    - [ ] CircleCI (with_circleci)
    - [ ] CodeCov (with_codecov)
    - [ ] GoCD (with_gocd)
    - [X] SonarCloud (Default)
- [ ] Infrastructure
    - [ ] Terraform (Deploy?)
    - [ ] S3 (with_awss3)
- [ ] Other
    - [ ] Lombok (with_lombok)
- [ ] Testing
    - [ ] Performance Testing
    - [ ] Load Testing?
    - [ ] Functional (end-to-end)?
    - [ ] User Acceptance?

All docs added to master - branches need to be cleansed after changes have occurred in master so
need to get master right first.

[Documentation](https://github.com/fishey2/java-component-template/wiki) for this, including testing Spring Boot 2 with JUnit5 and Mockito.

## Dependencies

The following dependencies are used within this project,

| Dependency               | Version        | Usage                                              | Licence            |
|--------------------------|----------------|:---------------------------------------------------|--------------------|
| spring-boot-dependencies | v2.2.6.RELEASE | Dependencies for Spring Boot 2                     | Apache 2.0         |
| spring-boot-starter-web  | v2.2.6.RELEASE | Spring Boot Web Starter for Configuring Spring App | Apache 2.0         |
| logback-classic          | v1.2.3         | Logging framework                                  | EPL 1.0 & LGPL 2.1 |
| springfox-swagger2       | v2.9.2         | API Auto Documentation                             | Apache 2.0         |
| springfox-swagger-ui     | v2.9.2         | Serves the Swagger Documentation on the Spring App | Apache 2.0         |

### Test Dependencies

| Dependency               | Version        | Usage                                                 | Licence    |
|--------------------------|----------------|-------------------------------------------------------|------------|
| junit-jupiter            | v5.5.2         | Testing Framework                                     | EPL 2.0    |
| spring-boot-starter-test | v2.2.6.RELEASE | Contains MockMVC for testing Spring Boot applications | Apache 2.0 |
| mockito-core             | v2.22.0        | Java test mocking libraries                           | MIT        |
| rest-assured             | v4.1.2         | API Test Framework                                    | Apache 2.0 |
| json-path                | v4.1.2         | JsonPath Implementation                               | Apache 2.0 |
| xml-path                 | v4.1.2         | XmlPath Implementation                                | Apache 2.0 | 
### Plugins

| Plugin                          | Version       | Usage                               |
|---------------------------------|---------------|:------------------------------------|
| java                            | -             | Gradle Java Plugin                  | 
| idea                            | -             | Gradle IntelliJ Plugin              |
| jacoco                          | -             | Code coverage plugin                |
| org.springframework.boot        | 2.2.6.RELEASE | Spring Boot plugin                  | 
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
## Reference Implementations

A list of Reference Implementations
- [Testing](docs/testing/index.md)
- [Messaging](docs/messaging/activemq.md)

## Developing

### Running Tests

```bash

// Running Unit and Integration Tests from CLI
./gradlew test

// Running Functional Tests from CLI
./gradlew functionalTest
```
### CodeCov

      - name: Upload coverage to Codecov
        uses: codecov/codecov-action@v1
        with:
          token: ${{ secrets.CODECOV_TOKEN }}
          file: ./build/reports/jacoco/test/jacocoUnitTestReport.xml
          flags: unittests
          name: codecov-umbrella

## SPring profiles

Set env variable

`SPRING_PROFILES_ACTIVE=test` 
