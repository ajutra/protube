package com.tecnocampus.LS2.protube_back.functional;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.Matchers;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryStepsDefs extends SpringFunctionalTesting {

    private String currentCategory;
    private MvcResult currentResult;

    @Given("the category {string} does not exist")
    public void theCategoryDoesNotExist(String category) throws Exception {
        currentCategory = category;
        mockMvc.perform(get("/api/categories/"+currentCategory))
                .andExpect(status().isNotFound());
    }

    @When("is created through REST call")
    public void isCreatedThroughRESTCall() throws Exception {
        String json = """
                {
                    "categoryName":""" + "\"" + currentCategory + "\"" + """
                }
                """;
        currentResult = mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andReturn();

    }

    @When("we query for category {string}")
    public void weQueryForCategory(String category) throws Exception {
        currentCategory = category;
        currentResult = mockMvc.perform(get("/api/categories/"+currentCategory))
                .andReturn();
    }

    @When("we query for all categories")
    public void weQueryForAllCategories() throws Exception {
        currentResult = mockMvc.perform(get("/api/categories"))
                .andReturn();
    }

    @Then("we obtain a {int} status code")
    public void weObtainAStatusCode(int status) {
        assertThat(currentResult.getResponse().getStatus(), is(Matchers.equalTo(status)));
    }
}
