package com.tecnocampus.LS2.protube_back.functional;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/resources")
public class FunctionalTests extends SpringFunctionalTesting {

}
