package com.roboautomator.component.config.swagger;

import static com.roboautomator.component.config.swagger.SwaggerConfig.APPLICATION_DESCRIPTION;
import static com.roboautomator.component.config.swagger.SwaggerConfig.APPLICATION_NAME;
import static com.roboautomator.component.config.swagger.SwaggerConfig.APPLICATION_VERSION;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springfox.documentation.spi.DocumentationType;

class SwaggerConfigTest {

    private SwaggerConfig testConfig;

    @BeforeEach
    void setTestConfig() {
        testConfig = new SwaggerConfig();
    }

    @Test
    void testApiConfigIsSwagger2() {
        assertThat(testConfig.api().getDocumentationType()).isEqualTo(DocumentationType.SWAGGER_2);
    }

    @Test
    void testIfSwaggerApiIsEnabled() {
        assertThat(testConfig.api().isEnabled()).isTrue();
    }

    @Test
    void testApiInfoIncludesCorrectInformation() {
        var apiInfo = testConfig.apiEndPointsInfo();

        assertThat(apiInfo.getDescription()).isEqualTo(APPLICATION_DESCRIPTION);
        assertThat(apiInfo.getTitle()).isEqualTo(APPLICATION_NAME);
        assertThat(apiInfo.getVersion()).isEqualTo(APPLICATION_VERSION);
    }
}
