package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.port.in.command.GetCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetCommentsByUsernameUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreCommentUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentRestControllerTests {

    private MockMvc mockMvc;

    @Mock
    private StoreCommentUseCase storeCommentUseCase;

    @Mock
    private GetCommentsByUsernameUseCase getAllCommentsUseCase;

    @InjectMocks
    private CommentRestController commentRestController;

    @BeforeEach
    void setUp() throws Exception {
        try (var ignored = MockitoAnnotations.openMocks(this)) {
            mockMvc = MockMvcBuilders.standaloneSetup(commentRestController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .build();
        }
    }

    @Test
    void storeCommentHandlesResourceAlreadyExistsException() throws Exception {
        StoreCommentCommand storeCommentCommand = TestObjectFactory.createDummyStoreCommentCommand("1");
        doThrow(new NoSuchElementException("Comment already exists")).when(storeCommentUseCase).storeComment(any());

        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(storeCommentCommand)))
                .andExpect(status().isNotFound());
    }

    @Test
    void storeCommentReturnsCreated() throws Exception {
        StoreCommentCommand storeCommentCommand = TestObjectFactory.createDummyStoreCommentCommand("1");

        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(storeCommentCommand)))
                .andExpect(status().isCreated());
    }

    @Test
    void getCommentsByUsernameReturnsListOfComments() throws Exception {
        String username = "existingUser";
        GetCommentCommand comment1 = TestObjectFactory.createDummyGetCommentCommand("1");
        GetCommentCommand comment2 = TestObjectFactory.createDummyGetCommentCommand("2");
        List<GetCommentCommand> comments = List.of(comment1, comment2);

        when(getAllCommentsUseCase.getCommentsByUsername(username)).thenReturn(comments);

        mockMvc.perform(get("/api/comments/user/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(comments)));
    }

    @Test
    void getCommentsByUsernameReturnsEmptyListWhenNoComments() throws Exception {
        String username = "nonExistentUser";

        when(getAllCommentsUseCase.getCommentsByUsername(username)).thenReturn(List.of());

        mockMvc.perform(get("/api/comments/user/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void getCommentsByUsernameHandlesException() throws Exception {
        String username = "userWithException";

        when(getAllCommentsUseCase.getCommentsByUsername(username)).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/api/comments/user/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
