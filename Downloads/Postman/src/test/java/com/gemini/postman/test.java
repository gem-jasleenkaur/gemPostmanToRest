package com.gemini.postman;

import io.restassured.RestAssured;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.junit.Test;

public class test {

    @Test
    public void testStatusCode() {
        // Given
        SerenityRest.given()
                .baseUri("https://gorest.co.in")
                .basePath("/public/v2/users")
                .when()
                .get();

        // When
        int statusCode = SerenityRest.then().extract().statusCode();

        // Then
        validateStatusCode(200);
    }

    @Step
    private void validateStatusCode(int statusCode) {
        SerenityRest
                .then()
                .statusCode(statusCode)
                .log().all();
    }
}