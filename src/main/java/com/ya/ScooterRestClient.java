package com.ya;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

//базовый клиент для всего приложения
public class ScooterRestClient {
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    protected RequestSpecification getBaseSpec(){
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .setBaseUri(BASE_URL)
                .build();
    }
}
