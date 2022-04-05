package com.ya;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierDataGenerator {
    public static Courier getRandomData() {
        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(courierLogin, courierPassword, courierFirstName);
    }

    public static Courier getDataWithNullName() {
        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = null;
        return new Courier(courierLogin, courierPassword, courierFirstName);
    }

    public static Courier getDataWithNullLogin() {
        String courierLogin = null;
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(courierLogin, courierPassword, courierFirstName);
    }

    public static Courier getDataWithNullPassword() {
        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        String courierPassword = null;
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(courierLogin, courierPassword, courierFirstName);
    }
}
