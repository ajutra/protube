package com.tecnocampus.LS2.protube_back.functional.user;

import com.tecnocampus.LS2.protube_back.functional.SpringFunctionalTesting;
import com.tecnocampus.LS2.protube_back.functional.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserStepDefs extends SpringFunctionalTesting {
    private final TestContext testContext;
    private String currentUser;
    private String videoId;
    private MvcResult currentUserResult;

    public UserStepDefs(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("the user {string} does not exist")
    public void theUserDoesNotExist(String user) throws Exception {
        currentUser = user;
        mockMvc.perform(get("/api/users/" + currentUser))
                .andExpect(status().isNotFound());
    }

    @When("this user is created through REST call")
    public void isCreatedThroughRESTCall() throws Exception {
        String json = """
                {
                    "username":""" + "\"" + currentUser + "\"" + """
                }
                """;
        currentUserResult = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        testContext.setCurrentResult(currentUserResult);
    }




}
