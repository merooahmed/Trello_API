package com.terrelloAPI;

import com.terrelloAPI.GetdefaultLists.GetDefaultsLists;
import com.terrelloAPI.GetdefaultLists.ListResponse;
import com.terrelloAPI.cards.CardsRequests;
import com.terrelloAPI.createBoard.BoardResponse;
import com.terrelloAPI.createBoard.CreateBoardRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

@Test(groups = "negative")
public class GetCardFromDeletedBoard {
    private String boardId;
    private CreateBoardRequest createBoardRequest;
    private CardsRequests cardsRequests;
    private GetDefaultsLists getDefaultsLists;
    private String todoListId;

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




    @Test(description = "Verify getting cards from a deleted board")
    public void shouldNotGetCardsFromDeletedBoard() {


        // Create Card
        cardsRequests.createCard("Test Card", todoListId);

        // Delete Board
        createBoardRequest.deleteBoard(boardId);

        // Try to get cards
        cardsRequests.getBoardCards(boardId)

                .then()

                .statusCode(404);
    }
}
