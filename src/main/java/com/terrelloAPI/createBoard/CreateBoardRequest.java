package com.terrelloAPI.createBoard;

import com.terrelloAPI.configs.ApiConfig;
import com.terrelloAPI.constants.EndPoints;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class CreateBoardRequest {

    /**
     * Used by all positive test cases.
     */
    @Step("Create Trello Board : {boardName}")
    public Response createBoard(String boardName) {
        return createBoard(boardName, ApiConfig.API_KEY, ApiConfig.API_TOKEN);
    }

    /**
     * Used by negative test cases.
     */
    @Step("Create Trello Board")
    public Response createBoard(String boardName, String apiKey, String apiToken) {

        RequestSpecification request = given()

                .filter(new AllureRestAssured())

                .contentType("application/json")

                .queryParam("defaultLists", true);

        if (apiKey != null) {
            request.queryParam("key", apiKey);
        }

        if (apiToken != null) {
            request.queryParam("token", apiToken);
        }

        if (boardName != null) {
            request.queryParam("name", boardName);
        }

        return request

                .log().all()

                .when()

                .post(EndPoints.CREATE_BOARD)

                .then()

                .log().all()

                .extract().response();
    }

    @Step("Delete Board")
    public Response deleteBoard(String boardId) {

        return given()

                .filter(new AllureRestAssured())

                .queryParam("key", ApiConfig.API_KEY)
                .queryParam("token", ApiConfig.API_TOKEN)

                .pathParam("id", boardId)

                .when()

                .delete(EndPoints.DELETE_BOARD)

                .then()

                .extract().response();
    }
}