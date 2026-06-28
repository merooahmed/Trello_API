package com.terrelloAPI;

import com.terrelloAPI.GetdefaultLists.ListResponse;
import com.terrelloAPI.createBoard.BoardResponse;
import com.terrelloAPI.createBoard.CreateBoardRequest;
import com.terrelloAPI.createCustomlist.CreateCustomList;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;
@Test(groups = "positive")
public class CreateCustomListTest {
    private String boardId;
    private  CreateBoardRequest createBoardRequest;
    private CreateCustomList createCustomList;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = com.terrelloAPI.configs.ApiConfig.BASE_URL;

        createBoardRequest = new CreateBoardRequest();

        Response createBoard = createBoardRequest.createBoard("swagLabBoard");

        BoardResponse board = createBoard.as(BoardResponse.class);

        boardId = board.getId();
    }

    @Test
    public void verifyUserCanCreateCustomList() {
        createCustomList = new CreateCustomList();


        Response response = createCustomList.createList(boardId, "Blocked");

        assertEquals(response.getStatusCode(), 200);

        ListResponse list = response.as(ListResponse.class);

        assertEquals(list.getName(), "Blocked");

        assertNotNull(list.getId());

    }
    @AfterClass(alwaysRun = true)
    public void cleanup() {

        if (boardId != null) {
            createBoardRequest.deleteBoard(boardId);
        }
    }


}
