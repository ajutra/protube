package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreUserCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.VerifyUserCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreUserUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.VerifyUserUseCase;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserRestControllerTests {
    private MockMvc mockMvc;

    @Mock
    StoreUserUseCase storeUserUseCase;

    @Mock
    VerifyUserUseCase verifyUserUseCase;

    @InjectMocks
    UserRestController userRestController;

    @BeforeEach
    void setUp() throws Exception {
        try (var ignored = MockitoAnnotations.openMocks(this)) {
            mockMvc = MockMvcBuilders.standaloneSetup(userRestController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .build();
        }
    }

    @Test
    void storeUserHandlesResourceAlreadyExistsException() throws Exception {
        StoreUserCommand storeUserCommand = TestObjectFactory.createDummyStoreUserCommand("validUsername");
        doThrow(new IllegalArgumentException("User already exists")).when(storeUserUseCase).storeUser(any());

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(storeUserCommand)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void storeUserReturnsCreated() throws Exception {
        StoreUserCommand storeUserCommand = TestObjectFactory.createDummyStoreUserCommand("validUsername");

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(storeUserCommand)))
                .andExpect(status().isCreated());
    }

    @Test
    void verifyUserAuthCredentialsReturnsOk() throws Exception {
        VerifyUserCommand verifyUserCommand = TestObjectFactory.createDummyVerifyUserCommand("1");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(verifyUserCommand)))
                .andExpect(status().isOk());
    }

    @Test
    void verifyUserAuthCredentialsReturnsBadRequest() throws Exception {
        VerifyUserCommand verifyUserCommand = TestObjectFactory.createDummyVerifyUserCommand("1");
        doThrow(new IllegalArgumentException("Invalid credentials")).when(verifyUserUseCase).verifyUser(any());

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(verifyUserCommand)))
                .andExpect(status().isBadRequest());
    }
}