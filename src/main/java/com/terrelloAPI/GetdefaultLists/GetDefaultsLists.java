package com.terrelloAPI.GetdefaultLists;

import com.terrelloAPI.configs.ApiConfig;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


public class GetDefaultsLists {
    @Step("Get board lists")
    public Response getBoardLists(String boardId){

        return given()
                .filter(new AllureRestAssured())

                .queryParam("key", ApiConfig.API_KEY)
                .queryParam("token", ApiConfig.API_TOKEN)

                .pathParam("boardId", boardId)

                .when()

                .get("/boards/{boardId}/lists")

                .then()

                .extract().response();
    }
}
