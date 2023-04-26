package com.template.integration;

import com.template.Configuration;
import com.template.ConfigurationManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import java.io.InputStream;

import static org.hamcrest.Matchers.lessThan;

public abstract class BaseTest {

    @BeforeClass
    public void setup() {

        Configuration configuration = ConfigurationManager.getConfiguration();

        String baseUri = String.format("%1$s:%2$s%3$s", configuration.baseURI(), configuration.port(), configuration.basePath());

        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
                .expectResponseTime(lessThan(20000L)).build();

        RestAssured.requestSpecification = requestSpecification;
        RestAssured.responseSpecification = responseSpecification;
    }

    protected InputStream createSchema(String jsonSchemaFileName) {
        return getClass().getClassLoader()
                .getResourceAsStream(jsonSchemaFileName);
    }
}
