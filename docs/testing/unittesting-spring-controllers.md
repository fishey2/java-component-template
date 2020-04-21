# Testing Controllers with Spring MVC

In this example we are using a Spring Boot 2 configuration, and testing with JUnit5:

| Dependency               | Version        | Usage                                                 | Licence    |
|--------------------------|----------------|-------------------------------------------------------|------------|
| junit-jupiter            | v5.5.2         | Testing Framework                                     | EPL 2.0    |
| spring-boot-starter-test | v2.2.6.RELEASE | Contains MockMVC for testing Spring Boot applications | Apache 2.0 |

The `spring-boot-starter-test` proves a mock Model View Controller for the purpose of testing.

An example test is demonstrated below using the `MockMvc`.

```java
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = HealthController.class)
@AutoConfigureMockMvc
public class HealthCheckTest {

    @MockBean
    private HealthService healthService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void checkHealthIsNotOk() throws Exception {

        doReturn(false).when(healthService).isHealthOk();

        mockMvc.perform(get("/health"))
                .andExpect(status().isServiceUnavailable());
    }
}
``` 

`@SpringBootTest` can be scoped to just provide the context that you require. However, you would need to mock all
dependent Beans. This can be achieved with the `@MockBean` annotation.

The `@AutoConfigureMockMvc` will auto-populate the `mockMvc` (also required `@Autowired`), with the correct context for
the scope of the test.

In the example we have mocked the `HealthService` to provide a false when `isHealthOk()` is called (using MockitoBDD).

Then we can use the `mockMvc` to `perform` a request to a specific endpoint `andExpect` certain information back.

In this case we want to `GET /health` and we expect a `SERVICE_UNAVAILABLE (503)` to be returned.

You can use `status()` to check the status (using the sprint test `ResultMatcher`) and you can use `content()` to check 
the content directly.

An alternative approach to checking the content is given below:

```java
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = HealthController.class)
@AutoConfigureMockMvc
public class HealthCheckTest {

    @MockBean
    private HealthService healthService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void checkHealthIsNotOk() throws Exception {

        doReturn(true).when(healthService).isHealthOk();

        MvcResult result = mockMvc.perform(get("/health"))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo("OK");
    }
}
```
___

[Table of Content](index.md) | [README](../../README.md)