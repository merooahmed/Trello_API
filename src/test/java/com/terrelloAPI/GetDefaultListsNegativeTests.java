package com.terrelloAPI;

import com.terrelloAPI.GetdefaultLists.GetDefaultsLists;
import com.terrelloAPI.configs.ApiConfig;
import com.terrelloAPI.createBoard.BoardResponse;
import com.terrelloAPI.createBoard.CreateBoardRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
@Test(groups = "negative")
public class GetDefaultListsNegativeTests {


    private final GetDefaultsLists getDefaultsLists = new GetDefaultsLists();


    @BeforeClass
    public void setup() {
        RestAssured.baseURI = com.terrelloAPI.configs.ApiConfig.BASE_URL;
    }


        @Test(description = "Verify that getting board lists with an invalid board id returns 404")
    public void shouldNotGetListsWithInvalidBoardId() {

        getDefaultsLists.getBoardLists(ApiConfig.INVALID_BOARDID)
                .then()
                .statusCode(404);
    }


}
