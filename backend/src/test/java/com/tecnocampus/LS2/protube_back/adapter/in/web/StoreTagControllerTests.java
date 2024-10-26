package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreTagCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreTagUseCase;
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

public class StoreTagControllerTests {
    private MockMvc mockMvc;

    @Mock
    StoreTagUseCase storeTagUseCase;

    @InjectMocks
    StoreTagController storeTagController;

    @BeforeEach
    void setUp() throws Exception {
        try (var ignored = MockitoAnnotations.openMocks(this)) {
            mockMvc = MockMvcBuilders.standaloneSetup(storeTagController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .build();
        }
    }

    @Test
    void storeTagHandlesResourceAlreadyExistsException() throws Exception {
        StoreTagCommand storeTagCommand = TestObjectFactory.createDummyStoreTagCommand("1");
        doThrow(new IllegalArgumentException("Tag already exists")).when(storeTagUseCase).storeTag(any());

        mockMvc.perform(post("/api/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(storeTagCommand)))
                .andExpect(content().string("Tag already exists"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void storeTagReturnsCreated() throws Exception {
        StoreTagCommand storeTagCommand = TestObjectFactory.createDummyStoreTagCommand("1");

        mockMvc.perform(post("/api/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(storeTagCommand)))
                .andExpect(status().isCreated());
    }
}
