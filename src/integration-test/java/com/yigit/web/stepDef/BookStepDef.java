package com.yigit.web.stepDef;

import com.yigit.web.utilities.RestUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;

import com.yigit.web.TestBase;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@CucumberContextConfiguration
@ContextConfiguration
public class BookStepDef extends TestBase{

    public static ResponseOptions<Response> response;

    @Given("Route user gets data from {string} resource with GET request")
    public void routeUserGetsDataFromResourceWithGETRequest(String url) {
        RestUtil restUtil= createEndpointConnection();
        response = restUtil.getReq(url);
        response.getBody().prettyPeek();
    }

    @Then("Verify that user gets success code {int}")
    public void verifyThatUserGetsSuccessCode(int successCode) {
        Assertions.assertEquals(response.getStatusCode(), successCode, "STATUS CODE SHOULD BE:");
    }
}
