package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreUserCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreUserUseCase;
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

public class StoreUserControllerTests {
    private MockMvc mockMvc;

    @Mock
    StoreUserUseCase storeUserUseCase;

    @InjectMocks
    StoreUserController storeUserController;

    @BeforeEach
    void setUp() throws Exception {
        try (var ignored = MockitoAnnotations.openMocks(this)) {
            mockMvc = MockMvcBuilders.standaloneSetup(storeUserController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .build();
        }
    }

    @Test
    void storeUserHandlesResourceAlreadyExistsException() throws Exception {
        StoreUserCommand storeUserCommand = TestObjectFactory.createDummyStoreUserCommand("validUsername");
        doThrow(new IllegalArgumentException("User already exists")).when(storeUserUseCase).storeUser(any());

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(storeUserCommand)))
                .andExpect(content().string("User already exists"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void storeUserReturnsCreated() throws Exception {
        StoreUserCommand storeUserCommand = TestObjectFactory.createDummyStoreUserCommand("validUsername");

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(storeUserCommand)))
                .andExpect(status().isCreated());
    }
}