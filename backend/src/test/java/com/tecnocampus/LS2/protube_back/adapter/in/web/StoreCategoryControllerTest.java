package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCategoryCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreCategoryUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StoreCategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StoreCategoryUseCase storeCategoryUseCase;

    @InjectMocks
    private StoreCategoryController storeCategoryController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(storeCategoryController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void storeCategoryReturnsCreated() throws Exception {
        StoreCategoryCommand storeCategoryCommand = TestObjectFactory.createDummyStoreCategoryCommand("1");

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(storeCategoryCommand)))
                .andExpect(status().isCreated());
    }

    @Test
    void storeCategoryHandlesResourceAlreadyExistsException() throws Exception {
        StoreCategoryCommand storeCategoryCommand = TestObjectFactory.createDummyStoreCategoryCommand("1");
        doThrow(new IllegalArgumentException("Category already exists")).when(storeCategoryUseCase).storeCategory(any());

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(storeCategoryCommand)))
                .andExpect(content().string("Category already exists"))
                .andExpect(status().isBadRequest());
    }
}
