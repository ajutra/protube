package com.tecnocampus.LS2.protube_back.functional.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.functional.SpringFunctionalTesting;
import com.tecnocampus.LS2.protube_back.functional.TestContext;
import com.tecnocampus.LS2.protube_back.port.in.command.GetCategoryCommand;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryStepsDefs extends SpringFunctionalTesting {

    private final TestContext testContext;
    private static String currentCategory;
    private MvcResult currentCategoryResult;

    public CategoryStepsDefs(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("the category {string} does not exist")
    public void theCategoryDoesNotExist(String category) throws Exception {
        currentCategory = category;
        mockMvc.perform(get("/api/categories/" + currentCategory))
                .andExpect(status().isNotFound());
    }

    @When("this category is created through REST call")
    public void isCreatedThroughRESTCall() throws Exception {
        String json = """
                {
                    "categoryName":""" + "\"" + currentCategory + "\"" + """
                }
                """;
        currentCategoryResult = mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andReturn();
        testContext.setCurrentResult(currentCategoryResult);
    }

    @When("we query for category {string}")
    public void weQueryForCategory(String category) throws Exception {
        currentCategory = category;
        currentCategoryResult = mockMvc.perform(get("/api/categories/" + currentCategory))
                .andReturn();
        testContext.setCurrentResult(currentCategoryResult);
    }

    @When("we query for all categories")
    public void weQueryForAllCategories() throws Exception {
        currentCategoryResult = mockMvc.perform(get("/api/categories"))
                .andReturn();
        testContext.setCurrentResult(currentCategoryResult);
    }

    @Then("we ensure the result is the queried category")
    public void weEnsureTheResultIsTheQueriedCategory() throws Exception {
        GetCategoryCommand getCategoryCommand = new GetCategoryCommand(currentCategory);
        mockMvc.perform(get("/api/categories/" + currentCategory))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(getCategoryCommand)));;
    }

    @Then("we ensure the result is a list of all categories")
    public void weEnsureTheResultIsAListOfAllCategories() throws Exception {
        GetCategoryCommand getCategoryCommand = new GetCategoryCommand(currentCategory);
        List<GetCategoryCommand> categories = List.of(getCategoryCommand);
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(categories)));
    }
}
