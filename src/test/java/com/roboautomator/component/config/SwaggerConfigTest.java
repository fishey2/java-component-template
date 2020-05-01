package com.roboautomator.component.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springfox.documentation.spi.DocumentationType;

import static com.roboautomator.component.config.SwaggerConfig.*;
import static org.assertj.core.api.Assertions.assertThat;

public class SwaggerConfigTest {

    private SwaggerConfig testConfig;

    @BeforeEach
    private void setTestConfig() {
        testConfig = new SwaggerConfig();
    }

    @Test
    public void testApiConfigIsSwagger2() {
        assertThat(testConfig.api().getDocumentationType()).isEqualTo(DocumentationType.SWAGGER_2);
    }

    @Test
    public void testIfSwaggerApiIsEnabled() {
        assertThat(testConfig.api().isEnabled()).isTrue();
    }

    @Test
    public void testApiInfoIncludesCorrectInformation() {
        var apiInfo = testConfig.apiEndPointsInfo();

        assertThat(apiInfo.getDescription()).isEqualTo(APPLICATION_DESCRIPTION);
        assertThat(apiInfo.getTitle()).isEqualTo(APPLICATION_NAME);
        assertThat(apiInfo.getVersion()).isEqualTo(APPLICATION_VERSION);
    }
}
