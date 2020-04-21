# Testing Logging within Spring Application

Spring uses `Slf4j`, which can be configured using custom appender for all sorts of scenarios.

We want to  be able to test if something has been logged from within the application, in this case a Spring Boot 2 
Application.

One approach to testing this is to create a test appender that will be added for the duration of the test. Then
you can use the utilities in `ILoggingEvent` to extract the pertinent information for the test.

A generic helper has been created within this project to make attaching an appender easy within a Spring Boot test:
- [AbstractLoggingTest](../../src/test/java/com/roboautomator/component/util/AbstractLoggingTest.java)

```java
public class TestClass extends AbstractLoggingTest<ClassUnderTest> {

   private MockMvc mockMvc;

   @BeforeEach
   public void setUpTest() {

       ClassUnderTest objectUnderTest = new ClassUnderTest();

       // Set's up the logger for the objectUnderTest
       setupLoggingAppender(objectUnderTest);

       mockMvc = MockMvcBuilders.standaloneSetup(objectUnderTest).build();
   }

   @Test
   public void shouldTestTheLogHasBeenUpdatedWithMessage() {

       //doSomething to invoke logging

       assertThat(getLoggingEventListAppender().list)
          .extracting(ILoggingEvent::getMessage) // What to extract see {@link ILoggingEvent}
          .contains("Log Message");
   }
}
```

`ILoggingEvent::getFormattedMessage` will add any additional token arguments that you have added,
which allows you to test both known and unknown/random data. `ILoggingEvent::getLoggingLevel` will get 
the log level to check that is also set correctly.

___

[Table of Content](index.md) | [README](../../README.md)