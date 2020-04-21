# Testing Spring Applications with RestAssured and Spring

In this example we use the RestAssured framework to perform the tests.

RestAssured can be used as a BDD based way of testing RESTful endpoints.

## Prerequisites

The Spring application needs to be running on the machine.

You can use `./gradlew functionalComposeUp` to launch the service locally.

`./gradlew functionalComposeDown` will bring the service back down.

## Example

In the simple example below, we are testing that when we perform `GET /health`
on the service, then we expect a 200 to be returned.

```java
public class HealthControllerFuncTest {

   @Test
   public void checkHealthReturns200() {
       given()
               .when()
               .get("http://localhost:8080/health")
               .then()
               .statusCode(200);
   }
}
```
___

[Table of Content](index.md) | [README](../../README.md)