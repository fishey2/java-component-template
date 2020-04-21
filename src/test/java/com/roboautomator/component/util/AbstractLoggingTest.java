package com.roboautomator.component.util;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.slf4j.LoggerFactory;

/**
 * <p>Helper abstract class to enable the collection of logs per scenario.</p>
 *
 * <br/>
 *
 * <b>Usage:</b>
 * <pre>
 *     public class TestClass extends AbstractLoggingTest&lt;ClassUnderTest&gt; {
 *
 *          private MockMvc mockMvc;
 *
 *          &#64;BeforeEach
 *          public void setUpTest() {
 *
*               ClassUnderTest objectUnderTest = new ClassUnderTest();
 *
 *              // Set's up the logger for the objectUnderTest
 *              setupLoggingAppender(objectUnderTest);
 *
 *              mockMvc = MockMvcBuilders.standaloneSetup(objectUnderTest).build();
 *          }
 *
 *          &#64;Test
 *          public void shouldTestTheLogHasBeenUpdatedWithMessage() {
 *
 *              //doSomething to invoke logging
 *
 *              assertThat(getLoggingEventListAppender().list)
 *                 .extracting(ILoggingEvent::getMessage) // What to extract see {@link ILoggingEvent}
 *                 .contains("Log Message");
 *          }
 *
 *          ...
 *     }
 * </pre>
 *
 * @param <T> the type T of the ClassUnderTest
 */
public abstract class AbstractLoggingTest<T> {

    private ListAppender<ILoggingEvent> loggingEventListAppender;

    /**
     * Sets up the Logging Appender for the test
     *
     * @param object the Object that is being tested
     */
    protected void setupLoggingAppender(T object) {
        loggingEventListAppender = setUpListAppender(object);
    }

    /**
     * Returns the {@link ListAppender} to be used within the tests.
     *
     * @return the instance of {@link ListAppender} of type {@link ILoggingEvent}
     */
    protected ListAppender<ILoggingEvent> getLoggingEventListAppender() {
        return loggingEventListAppender;
    }

    private ListAppender<ILoggingEvent> setUpListAppender(T object) {
        Logger logger = (Logger) LoggerFactory.getLogger(object.getClass());

        ListAppender<ILoggingEvent> loggingEventListAppender = new ListAppender<>();
        loggingEventListAppender.start();

        logger.addAppender(loggingEventListAppender);

        return loggingEventListAppender;
    }
}
