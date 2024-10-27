package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.port.in.command.GetCategoryCommand;
import com.tecnocampus.LS2.protube_back.port.out.GetCategoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GetCategoryServiceTests {

    @Mock
    private GetCategoryPort getCategoryPort;

    @InjectMocks
    private GetCategoryService getCategoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCategoryReturnsCategoryCommand() {
        String categoryName = "validCategory";
        Category category = TestObjectFactory.createDummyCategory(categoryName);
        GetCategoryCommand expectedCommand = TestObjectFactory.createDummyGetCategoryCommand(categoryName);
        when(getCategoryPort.getCategory(categoryName)).thenReturn(category);

        GetCategoryCommand result = getCategoryService.getCategory(categoryName);

        assertEquals(expectedCommand, result);
    }
}