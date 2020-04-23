# SonarCloud

| Package    | Version |
|------------|---------|
| OpenJDK    | 13      |
| Gradle     | 6.2.2   |
| jacoco     | 6.2.2   |
| sonarqube  | 2.8     |
| JUnit      | 5.5.2   |

## SonarCloud Setup

Setting-up [SonarCloud](https://sonarcloud.io) is quite simple, you can click on the integration of your choice and
follow the flow through to integrate. Once you have done that, and you are ready to add a project.

The detailed steps are:
* Click on GitHub symbol to authenticate and link with GitHub
* Choose the organization that you wish to link with
* Select the repositories that you wish to incorporate
* Continue, then create organization
* You can then select to analyse a new project
* Select the project you want to setup
* Setup for the CI tool/development environment of your choice

## Gradle Integration

### Prerequisites

The following pre-requisites are required:
1. Java installed and running
2. Testing framework is installed and running
3. Jacoco plugin is setup and working
4. Gradle is setup to work with the above

### Gradle `sonar.gradle`
```groovy
sonarqube {
    properties {
        // The projectKey for the project in Sonar (Organization + _ + Project)
        property "sonar.projectKey", "fishey2_java-component-template"
        
        // The organization associated with sonar (Organization or User)
        property "sonar.organization", "fishey2"
   
        // The sonar host, for SonarCloud this URL is correct
        property "sonar.host.url", "https://sonarcloud.io"
    
        // The login token provided by SonarCloud
        property "sonar.login", System.getenv("SONAR_TOKEN")
    }
}
```

```groovy
plugins {
    id "org.sonarqube" version "2.8"
}
```

It is generally bad practise to expose secrets, such as Access tokens, to the public. Hence, the `sonar.login` 
now gets the environment variable `SONAR_TOKEN`, which is stored as a GitHub Secret.

To run this successfully, assuming that jacoco is setup with the default config, you can use 
`./gradlew clean build jacocoTestReport sonarqube`.

The reason that we run jacocoTestReport directly before, is that sonarqube will look for the `.xml` generated from
jacoco plugin (which requires some additional config to get).

```gradle
jacocoTestReport {
    sourceSets sourceSets.main
    executionData test
    reports {
        xml.enabled true
        csv.enabled false
        html.enabled true
    }
}
```
Having multiple run configurations in gradle, I was required to set the sourceSets and executionData value. Then you 
can select what reports are generated in the build/jacoco folder.

If you do not do this, then SonarCloud will still integrate, however the coverage will not show.

## GitHub Actions

### Pre-requisites

1. You have a working GitHub Actions pipeline
2. You have saved the access token from SonarCLoud in GitHub Secrets as `SONAR_TOKEN`

### Configuration

There is a lot of conflicting advice on how to set Sonar integration within GitHub actions.

Some people are saying you should use the github actions plugin, which when used, it told me I should
not use it.

I opted for just running it from gradle like most of the other things I have configured in the Actions 
pipelines.

```yaml
jobs:
  test:
    runs-on: [ubuntu-latest]
    steps:
      - name: Checkout Sourcecode
        uses: actions/checkout@v1
      - name: Grant execute permission for gradlew
        run: chmod +x gradlew
      - name: Test with Coverage
        run: ./gradlew jacocoTestReport
      - name: Analysis with SonarCloud
        run: ./gradlew sonarqube
        env:
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
``` 

`sonarqube` does require the `GITHUB_TOKEN` within the pipeline, so even if you have the 
`SONAR_TOKEN` hard-coded in gradle, you will still need to provide it.

In the pipeline, I can assume that I am working on a clean environment, and I have jacocoTestReport building
before testing and generating the `xml` report.

When `sonarqube` runs, it has the `xml` files generates and has access to them.