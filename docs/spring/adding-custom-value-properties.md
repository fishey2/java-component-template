# Defining Custom @Value Properties

Spring handles variables using dependency injection, by defining `@Value("${path.to.prop}")`, it will look
for a `application.properties` file as shown in Example 1, or an `application.yml` file as shown in Example 2.

**Example 1 - application.properties**

```properties
spring.application.property=value
```

**Example 2 - application.yml**

```yaml
spring:
  application:
    property: value
```


Ones used as @Value

Defined in application.yml

Provide resources/META-INF/additional-spring-configuration-metadata.json

Install plugin for annotation processor

Refresh gradle

Will stop showing warnings for unspecified properties in yml file now (added validation)