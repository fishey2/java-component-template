package com.roboautomator.component.config.swagger;

import com.roboautomator.component.config.swagger.SwaggerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springfox.documentation.spi.DocumentationType;

import static com.roboautomator.component.config.swagger.SwaggerConfig.*;
import static org.assertj.core.api.Assertions.assertThat;

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
