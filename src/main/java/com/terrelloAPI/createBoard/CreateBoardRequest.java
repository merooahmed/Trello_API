package com.terrelloAPI.createBoard;

import com.terrelloAPI.configs.ApiConfig;
import com.terrelloAPI.constants.EndPoints;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CreateBoardRequest {

    @Step("Create Trello Board : {boardName}")
    public Response createBoard(String boardName){

        return given()

                .filter(new AllureRestAssured())

                .contentType("application/json")

                .queryParam("key", ApiConfig.API_KEY)
                .queryParam("token", ApiConfig.API_TOKEN)
                .queryParam("name", boardName)
                .queryParam("defaultLists", true)

                .log().all()

                .when()

                .post(EndPoints.CREATE_BOARD)

                .then()

                .log().all()

                .extract().response();
    }

    @Step("Delete Board")
    public Response deleteBoard(String boardId){

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