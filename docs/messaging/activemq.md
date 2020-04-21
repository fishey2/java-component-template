# ActiveMQ

Active MQ has been used as an message queue implementation. Specifically Java Messaging Service has been
implemented.

The following components will be covered:

 1. Docker (Setup)
 2. WebUI and Admin
 3. Environment Variables
 4. Producer
 5. Consumer
 
 Generic interface contracts have been developed for the config, consumer and producer, which are bound only to
 JMS type messages, but are agnostic to the implementation (ActiveMQ):
 - [QueueConfig](../../src/main/java/com/roboautomator/component/config/message/QueueConfig.java)
 - [QueueConsumer](../../src/main/java/com/roboautomator/component/config/message/QueueConsumer.java)
 - [QueueProducer](../../src/main/java/com/roboautomator/component/config/message/QueueProducer.java)
 
 ## Docker Setup
 
 There are two environments setup in this project. One is used generally for testing purposes.
 
```yaml
version: "3.7"
services:
  activemq:
    image: rmohr/activemq:5.15.9-alpine
    ports:
      - 61616:61616
      - 8161:8161
```

In this case, the JMS and ActiveMQ implementation defaults to port `61616`. A full list of protocols and associated 
ports are given below.

| Protocol | Port     |
|----------|----------|
| MQTT     | 1883     |
| AMQP     | 5672     |
| Stomp    | 61613    |
| OPENWIRE | 61616    |
| Any TCP  | 61616    |

## WebUI and Admin

The console port is provided by default on port `8161`, with username and password of `admin`.

Once you have logged in using the admin credentials, you can view, copy, move and create messages and queues.

## Environment Variables

Spring Boot 2 uses environment variables (set in `application.yml`) to configure the connection to ActiveMQ. These are 
set in `resources/application.yml`. The one in `main` is the `production` settings, the one in `test` overrides that 
of `main`. 

The following variables should be set and available to use within the Spring Boot Application.

```yaml
spring:
  activemq:
    broker-url: tcp://127.0.0.1:61616
    user: admin
    password: admin
```

These can be accessed from within the Spring Boot Application by using `@Value("${spring.activemq.broker-url}")` as an 
example.


## Spring Configuration

The Spring Configuration provides the context with a ConnectionFactory and JmsTemplate to be able to send messages.

In the example 
([ActiveMQConfig Example](../../src/main/java/com/roboautomator/component/config/message/activemq/ActiveMQConfig.java))
we lazily configure a CachingConnectionFactory to pull the same connection pool.

The example above also provides access to the reference implementations of the `ActiveMQConsumer` and `ActiveMQProducer`
to read and write to the queues.

## Producer

The role of the producer is to 'produce' messages to put onto the message queue (this is synonymous with 'publisher' 
depending on the domain).

Examples:
- [ActiveMQConsumer Example](../../src/main/java/com/roboautomator/component/config/message/activemq/consumer/ActiveMQConsumer.java) 

## Consumer

The role of the consumer is to 'consume' messages from the message queue (this is synonymous with 'subscriber' 
depending on the domain).

Examples:
- [ActiveMQProducer Example](../../src/main/java/com/roboautomator/component/config/message/activemq/producer/ActiveMQProducer.java) 

___

[README](../../README.md)