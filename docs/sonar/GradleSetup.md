# Setting up SonarCloud with Gradle

I have the Gradle scripts modularised, the plugin is defined in `build.gradle`:

```groovy
plugins {
    id "org.sonarqube" version "2.8"
}
```

The sonarqube setup is defined in `gradle/sonar.gradle` and imported into `build.gradle` using
`apply from: 'gradle/sonar.gradle'`.

```groovy
sonarqube {
    properties {
        property "sonar.projectKey", "fishey2_java-component-template"
        property "sonar.organization", "fishey2"
        property "sonar.host.url", "https://sonarcloud.io"
        property "sonar.login", "68c07855a5eebd70047e32071ae0b645556feaac"
    }
}
```

__________

[<------ BACK](./SonarQubeSetup.md) | [HOME](../index.md) | [NEXT ------>](./GitHubActionsSetup.md)

[![Actions Status](https://github.com/fishey2/java-component-template/workflows/Java%20CI/badge.svg)](https://github.com/fishey2/java-component-template/actions)
[![codecov](https://codecov.io/gh/fishey2/java-component-template/branch/master/graph/badge.svg?token=BuPjnBJ5YK)](https://codecov.io/gh/fishey2/java-component-template)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fishey2_java-component-template&metric=alert_status)](https://sonarcloud.io/dashboard?id=fishey2_java-component-template)


