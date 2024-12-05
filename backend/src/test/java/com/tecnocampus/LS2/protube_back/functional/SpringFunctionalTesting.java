package com.tecnocampus.LS2.protube_back.functional;

import com.tecnocampus.LS2.protube_back.ProtubeBackApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProtubeBackApplication.class)
@AutoConfigureMockMvc
public class SpringFunctionalTesting {
    @Autowired
    protected MockMvc mockMvc;
}
