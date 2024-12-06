package com.tecnocampus.LS2.protube_back.functional;

import io.cucumber.java.en.Then;
import org.hamcrest.Matchers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CommonSteps extends SpringFunctionalTesting {
    protected TestContext testContext;

    public CommonSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Then("we obtain a {int} status code")
    public void weObtainAStatusCode(int status) {
        assertThat(testContext.getCurrentResult().getResponse().getStatus(), is(Matchers.equalTo(status)));
    }
}
