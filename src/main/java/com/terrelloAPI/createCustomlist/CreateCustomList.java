package com.terrelloAPI.createCustomlist;

import com.terrelloAPI.configs.ApiConfig;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CreateCustomList {
    @Step("Create new list")
    public Response createList(String boardId, String listName) {

        return given()

                .filter(new AllureRestAssured())

                .queryParam("key", ApiConfig.API_KEY)
                .queryParam("token", ApiConfig.API_TOKEN)
                .queryParam("idBoard", boardId)
                .queryParam("name", listName)

                .when()

                .post("/lists")

                .then()

                .extract().response();
    }
}
