package com.ya;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.Random;

public class OrderDataGenerator {
    public static Order getRandomOrderData() {
        Random rand = new Random();
        String firstName = RandomStringUtils.randomAlphabetic(10);
        String lastName = RandomStringUtils.randomAlphabetic(10);
        String address = RandomStringUtils.randomAlphabetic(10);
        String metroStation = RandomStringUtils.randomAlphabetic(10);
        String phone = "+" + RandomStringUtils.randomNumeric(11);
        int rentTime = RandomUtils.nextInt(10, 12);
        String deliveryDate = "2000-12-31";
        String comment = RandomStringUtils.randomAlphabetic(10);
        String color = "BLACK";
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}
