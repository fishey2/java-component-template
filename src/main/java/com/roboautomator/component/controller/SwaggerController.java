package com.roboautomator.component.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class SwaggerController {

    @Value("${spring.swagger.endpoint}")
    private String redirectPath;

    @GetMapping
    public String redirectRootToSwaggerDocs() {
        return "redirect:" + redirectPath;
    }
}
