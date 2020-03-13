# SonarCloud GitHub Actions Setup

```yaml
name: Pipeline

on:
  push:
    branches:
      - master
  pull_request:
    branches:
      - master

jobs:
  test:
    runs-on: [ubuntu-latest]
    steps:
      - name: Checkout Source
        uses: actions/checkout@v1
      - name: Grant execute permission for gradlew
        run: chmod +x gradlew
      - name: Test with Coverage
        run: ./gradlew jacocoUnitTestReport
      - name: Analysis with SonarCloud
        run: ./gradlew sonarqube
        env:
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
```
__________

[<------ BACK](./GradleSetup.md) | [HOME](../index.md)

[![Actions Status](https://github.com/fishey2/java-component-template/workflows/Java%20CI/badge.svg)](https://github.com/fishey2/java-component-template/actions)
[![codecov](https://codecov.io/gh/fishey2/java-component-template/branch/master/graph/badge.svg?token=BuPjnBJ5YK)](https://codecov.io/gh/fishey2/java-component-template)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fishey2_java-component-template&metric=alert_status)](https://sonarcloud.io/dashboard?id=fishey2_java-component-template)

