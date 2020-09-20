# Logging with Spring Boot

When using the default logging, you may notice some logging does not display
the same as the core Spring Boot logs.

In these cases they are using a different logging configuration (Java Utility Logging (jul) or Log4j2).

If we want everything to stream through the same log handler, we would need some additional 
dependencies, in this project we are using logback, and required the following dependencies 
to get everything handled the same.

```groovy
implementation('ch.qos.logback:logback-classic:1.2.3')
implementation('org.apache.logging.log4j:log4j-to-slf4j:2.11.1')
implementation('org.slf4j:jul-to-slf4j:1.7.25')
```

## Outputting logs in JSON Format

To make the logs more parsable and generic, the logs should be in JSON
format.

In order to achieve this, we need some additional dependencies and changes
to the [logback configuration](../../src/main/resources/logback.xml).

The dependencies that were required for this are:

```groovy
implementation('ch.qos.logback:logback-classic:1.2.3')
implementation('ch.qos.logback.contrib:logback-json-classic:0.1.5')
implementation('ch.qos.logback.contrib:logback-jackson:0.1.5')
```

In the [logback configuration](../../src/main/resources/logback.xml) configuration, where the appenders have been defined, we would need to add the following:

```xml
<!-- Example of Console Appender -->
<appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
    <encoder class="ch.qos.logback.core.encoder.LayoutWrappingEncoder">
        <layout class="ch.qos.logback.contrib.json.classic.JsonLayout">
            <jsonFormatter class="ch.qos.logback.contrib.jackson.JacksonJsonFormatter"/>
            <appendLineSeparator>true</appendLineSeparator>
        </layout>
    </encoder>
</appender>
```

## Removing Spring Logo from Logging

To remove the Spring Logo from the logs, you will need to change the `banner-mode` in the 
[Spring configuration](../../src/main/resources/application.yml):

```yaml
spring:
  main:
    banner-mode: off
```

## Useful Links

* [Testing Logging](../testing/unittesting-spring-logging.md)