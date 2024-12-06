package com.tecnocampus.LS2.protube_back.functional;

import com.tecnocampus.LS2.protube_back.ProtubeBackApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@CucumberContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ProtubeBackApplication.class,
        properties =
        {
        "pro_tube.store.dir=c:",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/protube",
        "spring.datasource.username=root",
        "spring.datasource.password=secret",
        "spring.data.mongodb.uri=mongodb://root:secret@localhost:27017",
        "spring.data.mongodb.database=protube"
})
public class SpringFunctionalTesting {
    @Autowired
    protected MockMvc mockMvc;
}
