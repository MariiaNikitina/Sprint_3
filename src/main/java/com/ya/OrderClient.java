package com.ya;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends ScooterRestClient{
    private static final String ORDER_PATH = "/api/v1/orders";
    @Step("Create order {order, scooter color}")
    public ValidatableResponse createOrder(Order order, String scooterColor){
        String createRequestBody="{\"firstName\": \""+order.getFirstName()+"\",\n"
                + "\"lastName\": \""+order.getLastName()+"\",\n"
                + "\"address\": \""+order.getAddress()+"\",\n"
                + "\"metroStation\": \""+order.getMetroStation()+"\",\n"///должна быть строка
                + "\"phone\": \""+order.getPhone()+"\",\n"
                + "\"rentTime\": "+2+",\n"
                + "\"deliveryDate\": \""+order.getDeliveryDate()+"\",\n"
                + "\"comment\": \""+order.getComment()+"\",\n"
                + "\"color\": [\""+scooterColor+"\"]}";
        return given()
                .spec(getBaseSpec())
                .body(createRequestBody)
                .when()
                .post(ORDER_PATH)
                .then();
    }
    @Step("Cancel order {track}")
    public ValidatableResponse cancelOrder(int track){
        return given()
                .spec(getBaseSpec())
                .body(track)
                .when()
                .post(ORDER_PATH+"/cancel")
                .then();
    }
}


