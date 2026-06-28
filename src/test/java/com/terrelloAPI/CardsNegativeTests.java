package com.terrelloAPI;

import com.terrelloAPI.GetdefaultLists.GetDefaultsLists;
import com.terrelloAPI.cards.CardResponse;
import com.terrelloAPI.cards.CardsRequests;
import com.terrelloAPI.createBoard.BoardResponse;
import com.terrelloAPI.createBoard.CreateBoardRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(groups = "negative")

public class CardsNegativeTests {
    private String boardId;
    private CreateBoardRequest createBoardRequest;
    private CardsRequests cardsRequests;
    private GetDefaultsLists getDefaultsLists;
    private String todoListId;
    CardResponse secondCard;
    CardResponse firstCard;



    @BeforeClass
    public void setup() {
        RestAssured.baseURI = com.terrelloAPI.configs.ApiConfig.BASE_URL;

        createBoardRequest = new CreateBoardRequest();
        cardsRequests=new CardsRequests();

        Response createBoard = createBoardRequest.createBoard("swagLabBoard");

        BoardResponse board = createBoard.as(BoardResponse.class);

        boardId = board.getId();

    }
    @Test(description = "Create card without listId")
    public void createCardWithoutListId() {

        Response response = cardsRequests.createCard(null, "Test Card");

        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(response.asString(), "invalid value for idList");
    }


}
