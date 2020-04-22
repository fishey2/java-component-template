package com.roboautomator.component.util;

import org.junit.jupiter.api.Test;

import static com.roboautomator.component.util.StringHelper.cleanString;
import static org.assertj.core.api.Assertions.assertThat;

public class StringHelperTest {

    @Test
    public void shouldRemoveNewlineCharacters() {
        String testString = "Hello\nWorld";

        assertThat(cleanString(testString)).isEqualTo("HelloWorld");
    }

    @Test
    public void shouldRemoveCarriageReturnCharacters() {
        String testString = "Hello\rWorld";

        assertThat(cleanString(testString)).isEqualTo("HelloWorld");
    }

    @Test
    public void shouldRemoveTabCharacters() {
        String testString = "Hello\tWorld";

        assertThat(cleanString(testString)).isEqualTo("HelloWorld");
    }

    @Test
    public void shouldRemoveMultipleEscapeCharacters() {
        String testString = "He\nll\ro\t\nWor\r\rl\t\td";

        assertThat(cleanString(testString)).isEqualTo("HelloWorld");
    }
}
