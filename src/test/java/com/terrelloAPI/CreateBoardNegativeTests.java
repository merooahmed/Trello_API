package com.terrelloAPI;

import com.terrelloAPI.configs.ApiConfig;
import com.terrelloAPI.createBoard.CreateBoardRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
@Test(groups = "negative")
public class CreateBoardNegativeTests {

    private String boardId;
    private CreateBoardRequest createBoardRequest;

    @BeforeClass
    public void setup(){
        createBoardRequest=new CreateBoardRequest();
        RestAssured.baseURI = com.terrelloAPI.configs.ApiConfig.BASE_URL;
    }
    @Test(description = "Verify that creating a board without a name returns 400")
    public void shouldNotCreateBoardWithoutName() {

        Response response = createBoardRequest.createBoard(
                null,
                ApiConfig.API_KEY,
                ApiConfig.API_TOKEN
        );

        Assert.assertEquals(response.statusCode(), 400);

        Assert.assertTrue(response.asString().contains("name"));
    }



    @Test(description = "Create board without authentication")
    public void shouldNotCreateBoardWithoutAuthentication() {

        createBoardRequest.createBoard(
                        "Test Board",
                        null,
                        null
                )
                .then()
                .statusCode(401);
    }

    @AfterClass(alwaysRun = true)
    public void cleanup() {

        if (boardId != null) {
            createBoardRequest.deleteBoard(boardId);
        }
    }
}
