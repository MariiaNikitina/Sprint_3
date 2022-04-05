package com.ya;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class OrderTest {
    Order order;
    OrderClient orderClient;
    int track;
    private final String scooterColor;

    public OrderTest(String scooterColor) {
        this.scooterColor = scooterColor;
    }

    @Parameterized.Parameters(name = "Scooter color:{0}, {1}") // добавили аннотацию
    public static Object[][] getScooterColor() {
        return new Object[][]{
                {"BLACK"},
                {"WHITE"},
                {"BLACK\", \"WHITE"},
                {"\", \""},
                {"\", \"WHITE"},
                {"BLACK\", \""},
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = OrderDataGenerator.getRandomOrderData();
    }

    @After
    public void tearDown() {
        orderClient.cancelOrder(track);
    }

    @DisplayName("Create Order With different scooter Color")
    @Description("Checking order is created with different colors")
    @Test
    public void createOrderWithDifferentColor() {
        ValidatableResponse createResponse = orderClient.createOrder(order, scooterColor);
        int statusCode = createResponse.extract().statusCode();
        assertThat("Order creation doesn't send created message", statusCode, equalTo(SC_CREATED));
        track = createResponse.extract().path("track");
        assertThat("Returned track is equal to zero", track, is(not(0)));
    }
}
