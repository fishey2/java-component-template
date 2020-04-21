# Testing Controllers using Spring Boot Test 

Spring Boot Test can launch the service locally on a `RANDOM_PORT`, which is injected into the test to enable testing 
directly on a URL.

The example below demonstrates how you can use the `@LocalServerPort` provided by the spring context, to 
direct requests to an endpoint and test the response.

In the example, we are checking the `/health` endpoint returns 200.  

```java
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthControllerTestIT {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/health");
    }

    @Test
    public void getHello() {
        ResponseEntity<String> response = template.getForEntity(base.toString(),
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
```

In the example `TestRestTemplate` is provided by `spring-boot-starter-test` dependency.

___

[Table of Content](index.md) | [README](../../README.md)