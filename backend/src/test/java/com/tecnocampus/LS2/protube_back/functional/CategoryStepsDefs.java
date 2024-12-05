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

    private final TestContext testContext;
    private String currentCategory;
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
}
