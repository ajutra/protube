package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.port.in.command.GetUserVideoLikeAndDislikeCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreUserCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.EditUserVideoLikeOrDislikeUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetUserVideoLikeAndDislikeUseCase;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserRestControllerTests {
    private MockMvc mockMvc;

    @Mock
    StoreUserUseCase storeUserUseCase;

    @Mock
    private GetUserVideoLikeAndDislikeUseCase getUserVideoLikeAndDislikeUseCase;

    @Mock
    private EditUserVideoLikeOrDislikeUseCase editUserVideoLikeOrDislikeUseCase;

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

    @Test
    void getUserVideoLikeAndDislikeReturnsCommand() throws Exception {
        GetUserVideoLikeAndDislikeCommand command = new GetUserVideoLikeAndDislikeCommand(true, false);
        when(getUserVideoLikeAndDislikeUseCase.getUserVideoLikeAndDislike(anyString(), anyString())).thenReturn(command);

        mockMvc.perform(get("/api/users/{username}/videos/{videoId}/like-status", "validUsername", "validVideoId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(command)));
    }

    @Test
    void likeVideoReturnsOk() throws Exception {
        doNothing().when(editUserVideoLikeOrDislikeUseCase).likeVideo(anyString(), anyString());

        mockMvc.perform(post("/api/users/{username}/videos/{videoId}/like", "validUsername", "validVideoId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void dislikeVideoReturnsOk() throws Exception {
        doNothing().when(editUserVideoLikeOrDislikeUseCase).dislikeVideo(anyString(), anyString());

        mockMvc.perform(post("/api/users/{username}/videos/{videoId}/dislike", "validUsername", "validVideoId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void removeLikeOrDislikeReturnsOk() throws Exception {
        doNothing().when(editUserVideoLikeOrDislikeUseCase).removeLikeOrDislike(anyString(), anyString());

        mockMvc.perform(delete("/api/users/{username}/videos/{videoId}/likes", "validUsername", "validVideoId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}