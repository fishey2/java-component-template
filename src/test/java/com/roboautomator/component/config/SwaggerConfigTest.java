package com.roboautomator.component.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;

import static com.roboautomator.component.config.SwaggerConfig.*;
import static org.assertj.core.api.Java6Assertions.assertThat;

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
        ApiInfo testApiInfo = testConfig.apiEndPointsInfo();

        assertThat(testApiInfo.getDescription()).isEqualTo(APPLICATION_DESCRIPTION);
        assertThat(testApiInfo.getTitle()).isEqualTo(APPLICATION_NAME);
        assertThat(testApiInfo.getVersion()).isEqualTo(APPLICATION_VERSION);
    }
}
