package com.tecnocampus.LS2.protube_back.functional;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class TagStepsDefs extends SpringFunctionalTesting{

    private String currentTag;
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
}
