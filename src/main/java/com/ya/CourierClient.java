package com.ya;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
public class CourierClient extends ScooterRestClient {
    private static final String COURIER_PATH = "api/v1/courier/";
@Step("Login with credentials{credentials}")
    public ValidatableResponse login(CourierCredentials credentials){
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login")
                .then();
    }
    @Step("Create courier {courier}")
    public ValidatableResponse create(Courier courier){
        String registerRequestBody = "{\"login\":\"" + courier.getLogin() + "\","
                + "\"password\":\"" + courier.getPassword() + "\","
                + "\"firstName\":\"" + courier.getFirstName() + "\"}";
        return given()
                .spec(getBaseSpec())
                .body(registerRequestBody)
                .when()
                .post(COURIER_PATH)
                .then();
    }
    @Step("Delete courier using courierId {courierId}")
    public ValidatableResponse delete(int courierId){
        return given()
                .spec(getBaseSpec())
                .body(courierId)
                .when()
                .post(COURIER_PATH)
                .then();
    }
}
