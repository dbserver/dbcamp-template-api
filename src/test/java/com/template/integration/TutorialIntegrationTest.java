package com.template.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.when;

public class TutorialIntegrationTest extends BaseTest {

    @Test
    @Tag("E2E")
    @DisplayName("Should get all tutorials records")
    public void givenGetAll_whenJsonResponseConformsToSchema_thenSuccess() {
                when()
                .get("/tutorials")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .assertThat();
    }
}