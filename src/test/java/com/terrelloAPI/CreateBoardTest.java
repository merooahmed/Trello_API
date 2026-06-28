package com.terrelloAPI;

import com.terrelloAPI.configs.ApiConfig;
import com.terrelloAPI.createBoard.BoardResponse;
import com.terrelloAPI.createBoard.CreateBoardRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
@Test(groups = "positive")
public class CreateBoardTest {
    private String boardId;
    private  CreateBoardRequest createBoardRequest;

    @BeforeClass
    public void setup(){
createBoardRequest=new CreateBoardRequest();
        RestAssured.baseURI = com.terrelloAPI.configs.ApiConfig.BASE_URL;
    }

    @Test(description = "Verify user can create board successfully")
    public void testCreateBoardSuccessfully(){

        CreateBoardRequest createBoardRequest = new CreateBoardRequest();

        Response response =
                createBoardRequest.createBoard("Automation Board");

        assertEquals(response.getStatusCode(),200);

        BoardResponse board =
                response.as(BoardResponse.class);

        assertEquals(board.getName(),"Automation Board");

        assertNotNull(board.getId());

        assertFalse(board.isClosed());

        System.out.println(board.getId());

        // Cleanup

       /* Response deleteResponse =
                createBoardRequest.deleteBoard(board.getId());

        assertEquals(deleteResponse.statusCode(),200);*/
    }





    @AfterClass(alwaysRun = true)
    public void cleanup() {

        if (boardId != null) {
            createBoardRequest.deleteBoard(boardId);
        }
    }

}