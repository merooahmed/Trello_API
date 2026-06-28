package com.terrelloAPI.GetdefaultLists;

import com.terrelloAPI.configs.ApiConfig;
import com.terrelloAPI.constants.EndPoints;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class GetDefaultsLists {

    @Step("Get board lists")
    public Response getBoardLists(String boardId) {
        return getBoardLists(boardId, ApiConfig.API_KEY, ApiConfig.API_TOKEN);
    }

    @Step("Get board lists")
    public Response getBoardLists(String boardId, String apiKey, String apiToken) {

        RequestSpecification request = given()

                .filter(new AllureRestAssured());

        if (apiKey != null) {
            request.queryParam("key", apiKey);
        }

        if (apiToken != null) {
            request.queryParam("token", apiToken);
        }

        if (boardId != null) {
            request.pathParam("boardId", boardId);
        }

        return request

                .log().all()

                .when()

                .get(EndPoints.DEFAULT_LISTS)

                .then()

                .log().all()

                .extract().response();
    }
}