package com.ya;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest {
    CourierClient courierClient;
    Courier courier;
    int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierDataGenerator.getRandomData();
        courierClient.create(courier);
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Courier Can Login With Valid Credentials") // имя теста
    @Description("Checking courier's Id and status code of response corresponds to successful login of courier")
    public void courierCanLoginWithValidCredentials() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        int statusCode = loginResponse.extract().statusCode();
        courierId = loginResponse.extract().path("id");
        assertThat("Courier cannot login. Credentials are valid", statusCode, equalTo(SC_OK));
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }

    //с пустым полем пароля тест падает, приходит отличный от ожидаемого статус код(504 вместо 400)
    @Test
    @DisplayName("courier Cannot Login With Empty Password") // имя теста
    @Description("Checking courier login returns Bad Request status code when password is empty")
    public void courierCannotLoginWithEmptyPassword() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), null));
        int statusCode = loginResponse.extract().statusCode();
        assertThat("Courier can login with empty password", statusCode, equalTo(SC_BAD_REQUEST));
        String message = loginResponse.extract().path("message");
        assertThat("Warning message isn't sent", message, equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("courier Cannot Login With Empty Login") // имя теста
    @Description("Checking courier login returns Bad Request status code when login is empty")
    public void courierCannotLoginWithEmptyLogin() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(null, courier.getPassword()));
        int statusCode = loginResponse.extract().statusCode();
        assertThat("Courier can login with empty password", statusCode, equalTo(SC_BAD_REQUEST));
        String message = loginResponse.extract().path("message");
        assertThat("Warning message isn't sent", message, equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("courier Cannot Login With Incorrect Pair Login Password") // имя теста
    @Description("Checking courier login returns Not Found status code when pair of login and password isn't exist")
    public void courierCannotLoginWithIncorrectPairLoginPassword() {
        String incorrectPassword = "incorrectPassword";
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), incorrectPassword));
        int statusCode = loginResponse.extract().statusCode();
        assertThat("Courier can login with non exist pair login-password ", statusCode, equalTo(SC_NOT_FOUND));
        String message = loginResponse.extract().path("message");
        assertThat("Warning message isn't sent", message, equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("courier Cannot Login If He Is Not Exist") // имя теста
    @Description("Checking courier login returns Not Found when login isn't exist")
    public void courierCannotLoginIfHeIsNotExist() {
        String incorrectLogin = "incorrectLogin";
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(incorrectLogin, courier.getPassword()));
        int statusCode = loginResponse.extract().statusCode();
        assertThat("Courier can login with non exist login and exist password", statusCode, equalTo(SC_NOT_FOUND));
        String message = loginResponse.extract().path("message");
        assertThat("Warning message isn't sent", message, equalTo("Учетная запись не найдена"));
    }
}