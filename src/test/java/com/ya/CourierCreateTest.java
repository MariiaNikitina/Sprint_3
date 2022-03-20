package com.ya;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CourierCreateTest {
    CourierClient courierClient;
    Courier courier;


    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Create Courier With Valid Data") // имя теста
    @Description("Checking courier creation returns Created status code when data is valid")
    public void createCourierWithValidData() {
        courier = CourierDataGenerator.getRandomData();
        ValidatableResponse createResponse = courierClient.create(courier);
        int statusCode = createResponse.extract().statusCode();
        assertThat("The courier creation with valid data is failed", statusCode, equalTo(SC_CREATED));
        boolean message = createResponse.extract().path("ok");
        assertThat("", message, equalTo(true));
    }

    //Несоответствие спеки и действительного ответа
    @Test
    @DisplayName("cannot Create Two Couriers With the Same Data") // имя теста
    @Description("Checking courier creation returns conflict when data is the same for two couriers")
    public void cannotCreateTwoCouriersWithSameData() {
        courier = CourierDataGenerator.getRandomData();
        ValidatableResponse createResponse = courierClient.create(courier);
        ValidatableResponse createSecondResponse = courierClient.create(courier);
        int statusCode = createSecondResponse.extract().statusCode();
        assertThat("The second courier creation with valid data doesn't send conflict message", statusCode, equalTo(SC_CONFLICT));
        String message = createSecondResponse.extract().path("message");
        assertThat("The error message isn't equal to expected", message, equalTo("Этот логин уже используется"));
    }

    //В спеке не сказано, что firstName - необязательное поле. В теории без него не должен создаваться пользователь
    //но в спеке не отображено, какой ответ должен приходить на запрос с пустым значением имени
    @Test
    @DisplayName("cannot Create Courier With Null First name") // имя теста
    @Description("Checking courier creation returns Bad Request when first name is empty")
    public void cannotCreateCourierWithNullFirstName() {
        courier = CourierDataGenerator.getDataWithNullName();
        ValidatableResponse createResponse = courierClient.create(courier);
        int statusCode = createResponse.extract().statusCode();
        assertThat("The courier creation without firstName doesn't send bad request", statusCode, equalTo(SC_BAD_REQUEST));
        String message = createResponse.extract().path("message");
        assertThat("The error message isn't equal to expected", message, equalTo("Этот логин уже используется"));
    }

    @Test
    public void cannotCreateCourierWithNullPassword() {
        courier = CourierDataGenerator.getDataWithNullPassword();
        ValidatableResponse createResponse = courierClient.create(courier);
        int statusCode = createResponse.extract().statusCode();
        assertThat("The courier creation with null password doesn't send bad request", statusCode, equalTo(SC_BAD_REQUEST));
        String message = createResponse.extract().path("message");
        assertThat("The error message isn't equal to expected", message, equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("cannot Create Courier With Null Login") // имя теста
    @Description("Checking courier creation returns Bad Request when login is empty")
    public void cannotCreateCourierWithNullLogin() {
        courier = CourierDataGenerator.getDataWithNullLogin();
        ValidatableResponse createResponse = courierClient.create(courier);
        int statusCode = createResponse.extract().statusCode();
        assertThat("The courier creation with null login doesn't send bad request", statusCode, equalTo(SC_BAD_REQUEST));
        String message = createResponse.extract().path("message");
        assertThat("The error message isn't equal to expected", message, equalTo("Недостаточно данных для создания учетной записи"));
    }


}
