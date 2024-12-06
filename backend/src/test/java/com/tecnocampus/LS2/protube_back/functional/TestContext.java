package com.tecnocampus.LS2.protube_back.functional;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

@Getter
@Setter
@Component
public class TestContext {

    private MvcResult currentResult;
}
