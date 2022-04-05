package com.ya;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderList extends ScooterRestClient {
    private final String ORDER_LIST_PATH = "api/v1/orders";

    public ValidatableResponse getOrderList() {
        return given()
                .spec(getBaseSpec())
                .body("")
                .when()
                .get(ORDER_LIST_PATH)
                .then();
    }
}
