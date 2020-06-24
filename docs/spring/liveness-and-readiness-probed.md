# Spring Boot Actuator

When installed and management port configured (see below), then `/actuator/health` and `actuator/info` become live by 
default.

```yaml
management:
  server:
    port: 8081    
```

The default response of the `/actuator/health` endpoint is, returns 200:

```json
{
    "status": "UP"
}
```

This checks database and ActiveMQ automatically, you need to enable show-details to see this though.

```yaml
server:
  port: 8080
management:
  server:
    port: 8081
  endpoint:
    health:
      show-details: always
```

```json
{
    "status": "UP",
    "components": {
        "db": {
            "status": "UP",
            "details": {
                "database": "PostgreSQL",
                "result": 1,
                "validationQuery": "SELECT 1"
            }
        },
        "diskSpace": {
            "status": "UP",
            "details": {
                "total": 250685575168,
                "free": 103234105344,
                "threshold": 10485760
            }
        },
        "jms": {
            "status": "UP",
            "details": {
                "provider": "ActiveMQ"
            }
        },
        "ping": {
            "status": "UP"
        }
    }
}
```

