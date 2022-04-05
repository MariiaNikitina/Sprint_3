package com.ya;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OrderListTest {
    OrderList orderList;

    @Before
    public void setUp() {
        orderList = new OrderList();
    }

    @DisplayName("Get Order List With Empty Body ") // имя теста
    @Description("Checking basic order response") // описание теста
    @Test
    public void getOrderListWithEmptyBody() {
        ValidatableResponse listOfOrders = orderList.getOrderList();
        int statusCode = listOfOrders.extract().statusCode();
        ArrayList<String> orders = listOfOrders.extract().path("orders");
        assertThat("The order list doesn't send", statusCode, equalTo(SC_OK));
        assertThat("The order list is empty", orders.size(), is(not(0)));
    }
}
