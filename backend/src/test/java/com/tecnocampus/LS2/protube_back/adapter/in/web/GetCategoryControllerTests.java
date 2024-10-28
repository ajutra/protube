package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.port.in.command.GetCategoryCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllCategoriesUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetCategoryControllerTests {

    private MockMvc mockMvc;

    @Mock
    private GetAllCategoriesUseCase getAllCategoriesUseCase;

    @InjectMocks
    private GetCategoryController getCategoryController;

    @BeforeEach
    void setUp() throws Exception {
        try (var ignored = MockitoAnnotations.openMocks(this)) {
            mockMvc = MockMvcBuilders.standaloneSetup(getCategoryController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .build();
        }
    }

    @Test
    void getAllCategoriesReturnsListOfCategories() throws Exception {
        GetCategoryCommand category1 = TestObjectFactory.createDummyGetCategoryCommand("1");
        GetCategoryCommand category2 = TestObjectFactory.createDummyGetCategoryCommand("2");
        List<GetCategoryCommand> categories = List.of(category1, category2);

        when(getAllCategoriesUseCase.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                 [
                                   {
                                     "categoryName": "Category name 1"
                                   },
                                   {
                                     "categoryName": "Category name 2"
                                   }
                                 ]
                         """));
    }

    @Test
    void getAllCategoriesReturnsEmptyListWhenNoCategories() throws Exception {
        when(getAllCategoriesUseCase.getAllCategories()).thenReturn(List.of());

        mockMvc.perform(get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void getAllCategoriesHandlesException() throws Exception {
        when(getAllCategoriesUseCase.getAllCategories()).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
