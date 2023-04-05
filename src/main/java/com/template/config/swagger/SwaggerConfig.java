package com.template.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;

@Configuration
//@EnableSwagger2
public class SwaggerConfig {

    //  Running in http://localhost:4767/swagger-ui.html/

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
//                .apis(RequestHandlerSelectors.basePackage("com.template.presentation.controller"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, Arrays.asList(
                        new ResponseBuilder().code("500").description("500 message").build(),
                        new ResponseBuilder().code("403").description("Forbidden!!!!!").build()
                ));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Template REST API",
                "Template para API REST",
                "API TOS",
                "Terms of service",
                new Contact("DbServer", "dbserver.com.br", "user@db.tec.br"), "License of API", "API license URL", Collections.emptyList());
    }
}