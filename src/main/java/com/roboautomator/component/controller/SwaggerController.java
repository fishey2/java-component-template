package com.roboautomator.component.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class SwaggerController {

    private static final String REDIRECT_PATH = "/swagger-ui.html";

    @RequestMapping("/")
    public String redirectRootToSwaggerDocs() {
        return "redirect:" + REDIRECT_PATH;
    }
}
