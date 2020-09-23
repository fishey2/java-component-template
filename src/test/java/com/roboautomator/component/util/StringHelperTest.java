package com.roboautomator.component.util;

import org.junit.jupiter.api.Test;

import static com.roboautomator.component.util.StringHelper.cleanString;
import static org.assertj.core.api.Assertions.assertThat;

class StringHelperTest {

    @Test
    void shouldRemoveNewlineCharacters() {
        var testString = "Hello\nWorld";

        assertThat(cleanString(testString)).isEqualTo("HelloWorld");
    }

    @Test
    void shouldRemoveCarriageReturnCharacters() {
        var testString = "Hello\rWorld";

        assertThat(cleanString(testString)).isEqualTo("HelloWorld");
    }

    @Test
    void shouldRemoveTabCharacters() {
        var testString = "Hello\tWorld";

        assertThat(cleanString(testString)).isEqualTo("HelloWorld");
    }

    @Test
    void shouldRemoveMultipleEscapeCharacters() {
        var testString = "He\nll\ro\t\nWor\r\rl\t\td";

        assertThat(cleanString(testString)).isEqualTo("HelloWorld");
    }
}
