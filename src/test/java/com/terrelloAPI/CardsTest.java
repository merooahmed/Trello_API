package com.terrelloAPI;

import com.terrelloAPI.GetdefaultLists.GetDefaultsLists;
import com.terrelloAPI.GetdefaultLists.ListResponse;
import com.terrelloAPI.cards.CardResponse;
import com.terrelloAPI.cards.CardsRequests;
import com.terrelloAPI.createBoard.BoardResponse;
import com.terrelloAPI.createBoard.CreateBoardRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.*;
@Test(groups = "positive")
public class CardsTest {

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

            getDefaultsLists= new GetDefaultsLists();
            Response listsResponse = getDefaultsLists.getBoardLists(boardId);

            List<ListResponse> lists =
                    listsResponse.jsonPath().getList("", ListResponse.class);

            todoListId = lists.stream()
                    .filter(list -> list.getName().equals("To Do"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("To Do list not found"))
                    .getId();
        }



    @Test(priority = 1)
    public void verifyUserCanCreateSetupPaymentGatewayCard() {

        Response response =
                cardsRequests.createCard(todoListId,
                        "Setup payment gateway");

        assertEquals(response.getStatusCode(),200);

         firstCard =
                response.as(CardResponse.class);

        assertEquals(firstCard.getName(),
                "Setup payment gateway");

        assertNotNull(firstCard.getId());

    }


    @Test(priority = 2)
    public void verifyUserCanCreateDesignHomepageCard() {
        Response response =
                cardsRequests.createCard(todoListId,
                        "Design homepage");

        assertEquals(response.getStatusCode(),200);

         secondCard =
                response.as(CardResponse.class);

        assertEquals(secondCard.getName(),
                "Design homepage");

        assertNotNull(secondCard.getId());

    }
    @Test(priority = 3)
    public void verifyCardDeleted(){

        Response deleteResponse =
                cardsRequests.deleteCard(secondCard.getId());
        assertEquals(deleteResponse.getStatusCode(), 200);

        Response cardsResponse =
                cardsRequests.getBoardCards(boardId);

        assertEquals(cardsResponse.getStatusCode(), 200);

        List<CardResponse> cards =
                cardsResponse.jsonPath().getList("", CardResponse.class);

        assertFalse(
                cards.stream()
                        .anyMatch(card -> card.getId().equals(secondCard.getId()))
        );

        assertTrue(
                cards.stream()
                        .anyMatch(card -> card.getId().equals(firstCard.getId()))
        );






    }

    @AfterClass(alwaysRun = true)
    public void cleanup() {

        if (boardId != null) {
            createBoardRequest.deleteBoard(boardId);
        }
    }


}


