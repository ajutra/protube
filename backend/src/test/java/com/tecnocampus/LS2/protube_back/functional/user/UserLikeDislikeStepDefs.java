package com.tecnocampus.LS2.protube_back.functional.user;

import com.tecnocampus.LS2.protube_back.functional.SpringFunctionalTesting;
import com.tecnocampus.LS2.protube_back.functional.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserLikeDislikeStepDefs extends SpringFunctionalTesting {
    private final TestContext testContext;
    private String currentUser;
    private String videoId;
    private MvcResult currentUserResult;

    public UserLikeDislikeStepDefs(TestContext testContext) {
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

    @Given("the user {string}")
    public void theUser(String user) {
        this.currentUser = user;
    }

    @And("an existing video")
    public void theVideoId() throws Exception {
        currentUserResult = mockMvc.perform(get("/api/videos"))
                .andReturn();

        videoId = currentUserResult.getResponse().getContentAsString().split("\"videoId\"\\s*:\\s*\"")[1].split("\"")[0];
    }

    @When("this user likes this video")
    public void thisUserLikesThisVideo() throws Exception {
        currentUserResult =
                mockMvc.perform(post("/api/users/" + currentUser + "/videos/" + videoId + "/like"))
                .andReturn();
        testContext.setCurrentResult(currentUserResult);
    }

    @When("this user dislikes this video")
    public void thisUserDislikesThisVideo() throws Exception {
        currentUserResult =
                mockMvc.perform(post("/api/users/" + currentUser + "/videos/" + videoId + "/dislike"))
                .andReturn();
        testContext.setCurrentResult(currentUserResult);
    }

    @When("this user deletes the like or dislike from this video")
    public void thisUserDeletesTheLikeOrDislikeFromThisVideo() throws Exception {
        currentUserResult =
                mockMvc.perform(delete("/api/users/" + currentUser + "/videos/" + videoId + "/likes"))
                .andReturn();
        testContext.setCurrentResult(currentUserResult);
    }


}
