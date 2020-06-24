package com.roboautomator.component.service.health;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class HealthTest {

    @Test
    void shouldBuildHealth() {
        var healthBuilder = Health.builder();

        assertThat(healthBuilder.toString()).contains("alive=false");
        assertThat(healthBuilder.toString()).contains("writable=false");
        assertThat(healthBuilder.toString()).contains("readable=false");
        assertThat(healthBuilder.build().isAlive()).isFalse();
        assertThat(healthBuilder.build().isReadable()).isFalse();
        assertThat(healthBuilder.build().isWritable()).isFalse();
    }
}
