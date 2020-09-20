# Java Component Template 

## GitHub Actions Overall

[![Actions Status](https://github.com/fishey2/java-component-template/workflows/Java%20CI/badge.svg)](https://github.com/fishey2/java-component-template/actions)

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
git pull origin with_activemq

// Pull in the reference implementation for PostgreSQL
git pull origin with_postgresql

// Pull in the reference implementation for Flyway DB Migrations
git pull origin with_flyway
```

List of branches:
- [with_codecov](https://github.com/fishey2/java-component-template/tree/with_codecov)
- [with_kafka](https://github.com/fishey2/java-component-template/tree/with_kafka)

## Testing

Currently Implemented Quality Gates:

| Gate Number | Description                  | Condition            |
|------------:|------------------------------|----------------------|
|           1 | Unit Tests                   | 100% PASS            |
|           2 | Integration Tests            | 100% PASS            |
|           3 | Jacoco (Code Coverage)       | 100% BRANCH COVERAGE |
|           4 | SonarCloud (Static Analysis) | NOT IMPLEMENTED      |
|           5 | Functional Tests             | 100% PASS            |

### Development

For development, it is suggested that you run using Gradle and bring up docker yourself.

```bash
$ docker-compose up -d

$ gradle test

$ docker-compose down
```

### Unit/Integration

To run the unit/integration tests

```bash
$ make test
```

### Functional

For functional tests, you should use make, which will build the application and docker container

```bash
$ make testFunctional
```

This runs the equivalent of
```bash

# Creates java executable
$ ./gradlew bootJar

# Builds the docker container
$ docker-compose -f docker-compose-test.yml build

# Launches the docker container
$ docker-compose -f docker-compose-test.yml up -d

# Waits for service locally
./scripts/wait-for-url.sh http://localhost:8080

# Runs the tests
$ ./gradlew functionalTest

# Tears down containers
$ docker-compose -f docker-compose-test.yml down
```

## Reference Implementations

A list of Reference Implementations
- [Testing](docs/testing/index.md)
- [Messaging](docs/messaging/activemq.md)

## To be converted to docs

### CodeCov

      - name: Upload coverage to Codecov
        uses: codecov/codecov-action@v1
        with:
          token: ${{ secrets.CODECOV_TOKEN }}
          file: ./build/reports/jacoco/test/jacocoUnitTestReport.xml
          flags: unittests
          name: codecov-umbrella

### Spring profiles

Set env variable

`SPRING_PROFILES_ACTIVE=test` 
