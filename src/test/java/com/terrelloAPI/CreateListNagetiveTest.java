package com.terrelloAPI;

import com.terrelloAPI.createCustomlist.CreateCustomList;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
@Test(groups = "negative")

public class CreateListNagetiveTest {
    private CreateCustomList createCustomList;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = com.terrelloAPI.configs.ApiConfig.BASE_URL;
    }

    @Test(description = "Verify that a list cannot be created without board id")
    public void shouldNotCreateListWithoutBoardId() {
        createCustomList = new CreateCustomList();

        createCustomList.createList(
                        null,
                        "Test List"
                )
                .then()
                .statusCode(400);
    }
}
