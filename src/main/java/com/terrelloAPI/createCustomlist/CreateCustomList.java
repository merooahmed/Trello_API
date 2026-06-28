package com.terrelloAPI.createCustomlist;

import com.terrelloAPI.configs.ApiConfig;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class CreateCustomList {
    @Step("Create new list")
    public Response createList(String boardId, String listName) {
        return createList(boardId, listName, ApiConfig.API_KEY, ApiConfig.API_TOKEN);
    }

    @Step("Create new list")
    public Response createList(String boardId, String listName, String apiKey, String apiToken) {

        RequestSpecification request = given()

                .filter(new AllureRestAssured());

        if (apiKey != null) {
            request.queryParam("key", apiKey);
        }

        if (apiToken != null) {
            request.queryParam("token", apiToken);
        }

        if (boardId != null) {
            request.queryParam("idBoard", boardId);
        }

        if (listName != null) {
            request.queryParam("name", listName);
        }

        return request

                .log().all()

                .when()

                .post("/lists")

                .then()

                .log().all()

                .extract().response();
    }
}
