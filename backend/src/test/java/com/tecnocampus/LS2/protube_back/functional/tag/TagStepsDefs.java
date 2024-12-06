package com.tecnocampus.LS2.protube_back.functional.tag;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.functional.SpringFunctionalTesting;
import com.tecnocampus.LS2.protube_back.functional.TestContext;
import com.tecnocampus.LS2.protube_back.port.in.command.GetTagCommand;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class TagStepsDefs extends SpringFunctionalTesting {

    private static String currentTag;
    private final TestContext testContext;
    private MvcResult currentTagResult;

    public TagStepsDefs(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("the tag {string} does not exist")
    public void theTagDoesNotExist(String tag) throws Exception {
        currentTag = tag;
        mockMvc.perform(get("/api/tags/" + currentTag))
                .andExpect(status().isNotFound());
    }

    @When("this tag is created through REST call")
    public void isCreatedThroughRESTCall() throws Exception {
        String json = """
                {
                    "tagName":""" + "\"" + currentTag + "\"" + """
                }
                """;
        currentTagResult = mockMvc.perform(post("/api/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andReturn();
        testContext.setCurrentResult(currentTagResult);
    }

    @When("we query for tag {string}")
    public void weQueryForTag(String tag) throws Exception {
        currentTag = tag;
        currentTagResult = mockMvc.perform(get("/api/tags/" + currentTag))
                .andReturn();
        testContext.setCurrentResult(currentTagResult);
    }

    @When("we query for all tags")
    public void weQueryForAllTags() throws Exception {
        currentTagResult = mockMvc.perform(get("/api/tags"))
                .andReturn();
        testContext.setCurrentResult(currentTagResult);
    }

    @Then("we ensure the result is the queried tag")
    public void weEnsureTheResultIsTheQueriedTag() throws Exception {
        GetTagCommand getTagCommand = new GetTagCommand(currentTag);
        mockMvc.perform(get("/api/tags/" + currentTag))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(getTagCommand)));

    }

    @Then("we ensure the result is a list of all tags")
    public void weEnsureTheResultIsAListOfAllTags() throws Exception {
        GetTagCommand getTagCommand = new GetTagCommand(currentTag);
        List<GetTagCommand> tags = List.of(getTagCommand);
        mockMvc.perform(get("/api/tags"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(tags)));
    }
}
