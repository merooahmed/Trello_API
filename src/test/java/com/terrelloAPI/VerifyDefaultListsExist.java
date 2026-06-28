package com.terrelloAPI;

import com.terrelloAPI.GetdefaultLists.GetDefaultsLists;
import com.terrelloAPI.GetdefaultLists.ListResponse;
import com.terrelloAPI.createBoard.BoardResponse;
import com.terrelloAPI.createBoard.CreateBoardRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
@Test(groups = "positive")
public class VerifyDefaultListsExist {
    private String boardId;
    private  CreateBoardRequest createBoardRequest;


    @BeforeClass
    public void setup() {
        RestAssured.baseURI = com.terrelloAPI.configs.ApiConfig.BASE_URL;

           createBoardRequest = new CreateBoardRequest();

        Response createBoard = createBoardRequest.createBoard("swagLabBoardnnnnnn");

        BoardResponse board = createBoard.as(BoardResponse.class);

        boardId = board.getId();
    }
    @Test
    public void verifyDefaultListsExist() {
GetDefaultsLists getDefaultsLists=new GetDefaultsLists();
        Response response = getDefaultsLists.getBoardLists(boardId);

        assertEquals(response.getStatusCode(), 200);

        List<ListResponse> lists =
                response.jsonPath().getList("", ListResponse.class);

        assertEquals(lists.size(), 3);

        assertTrue(
                lists.stream()
                        .anyMatch(l -> l.getName().equals("To Do"))
        );

        assertTrue(
                lists.stream()
                        .anyMatch(l -> l.getName().equals("Doing"))
        );

        assertTrue(
                lists.stream()
                        .anyMatch(l -> l.getName().equals("Done"))
        );
    }

    @AfterClass(alwaysRun = true)
    public void cleanup() {

        if (boardId != null) {
            createBoardRequest.deleteBoard(boardId);
        }
    }


}
