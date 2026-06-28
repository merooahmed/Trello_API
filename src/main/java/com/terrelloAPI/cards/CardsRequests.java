package com.terrelloAPI.cards;

import com.terrelloAPI.configs.ApiConfig;
import com.terrelloAPI.constants.EndPoints;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.terrelloAPI.configs.ApiConfig.API_KEY;
import static com.terrelloAPI.configs.ApiConfig.API_TOKEN;
import static io.restassured.RestAssured.given;

public class CardsRequests {
    @Step("Create {cardName} card")
    public Response createCard(String listId, String cardName) {

        RequestSpecification request = given()
                .filter(new AllureRestAssured())
                .queryParam("key", API_KEY)
                .queryParam("token", API_TOKEN);

        if (listId != null) {
            request.queryParam("idList", listId);
        }

        if (cardName != null) {
            request.queryParam("name", cardName);
        }

        return request
                .when()
                .post(EndPoints.CREATE_CARD)
                .then()
                .extract()
                .response();
    }

@Step("get cards from selected list")
    public Response getBoardCards(String boardId){

        return given()
                .filter(new AllureRestAssured())

                .queryParam("key",API_KEY)
                .queryParam("token",API_TOKEN)

                .pathParam("boardId",boardId)

                .when()

                .get(EndPoints.GET_CARDS)

                .then()

                .extract().response();
    }
@Step("delete selected card  {cardId}")
    public Response deleteCard(String cardId) {

        return given()
                .filter(new AllureRestAssured())

                .queryParam("key", ApiConfig.API_KEY)
                .queryParam("token", ApiConfig.API_TOKEN)

                .pathParam("cardId", cardId)

                .when()

                .delete(EndPoints.DELETE_CARD)

                .then()

                .extract().response();
    }
}
