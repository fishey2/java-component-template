package com.roboautomator.component.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    public static final String APPLICATION_BASE = "com.roboautomator.component";
    public static final String APPLICATION_NAME = "Java Component Template";
    public static final String APPLICATION_DESCRIPTION = "A component template for a Java based project, using JUnit, Swagger, Spring";
    public static final String APPLICATION_VERSION = "0.1-SNAPSHOT";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors
                .basePackage(APPLICATION_BASE))
            .paths(PathSelectors.any())
            .build().apiInfo(apiEndPointsInfo());
    }

    public ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title(APPLICATION_NAME)
            .description(APPLICATION_DESCRIPTION)
            .version(APPLICATION_VERSION)
            .build();
    }
}