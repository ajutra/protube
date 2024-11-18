package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.port.in.command.EditCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.GetCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.EditCommentUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllCommentsByVideoUseCase;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class CommentRestControllerTests {

    private MockMvc mockMvc;

    @Mock
    private StoreCommentUseCase storeCommentUseCase;

    @Mock
    private GetCommentsByUsernameUseCase getAllCommentsUseCase;

    @Mock
    private GetAllCommentsByVideoUseCase getAllCommentsByVideoUseCase;

    @Mock
    private EditCommentUseCase editCommentUseCase;

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
    void testGetCommentsByVideoId() throws Exception {
        List<GetCommentCommand> expected = List.of(
                TestObjectFactory.createDummyGetCommentCommand("1"),
                TestObjectFactory.createDummyGetCommentCommand("2")
        );

        when(getAllCommentsByVideoUseCase.getAllCommentsByVideoId(any())).thenReturn(expected);

        mockMvc.perform(get("/api/videos/testId/comments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expected)));
    }

    @Test
    void getCommentsByVideoId_returnsNotFoundWhenVideoNotFound() throws Exception {
        when(getAllCommentsByVideoUseCase.getAllCommentsByVideoId(any())).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get("/api/videos/testId/comments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCommentsByVideoId_returnsEmptyListWhenNoComments() throws Exception {
        when(getAllCommentsByVideoUseCase.getAllCommentsByVideoId(any())).thenReturn(List.of());

        mockMvc.perform(get("/api/videos/testId/comments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void getCommentsByUsernameReturnsListOfComments() throws Exception {
        String username = "existingUser";
        GetCommentCommand comment1 = TestObjectFactory.createDummyGetCommentCommand("1");
        GetCommentCommand comment2 = TestObjectFactory.createDummyGetCommentCommand("2");
        List<GetCommentCommand> comments = List.of(comment1, comment2);

        when(getAllCommentsUseCase.getCommentsByUsername(username)).thenReturn(comments);

        mockMvc.perform(get("/api/users/{username}/comments", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(comments)));
    }

    @Test
    void getCommentsByUsernameReturnsEmptyListWhenNoComments() throws Exception {
        String username = "nonExistentUser";

        when(getAllCommentsUseCase.getCommentsByUsername(username)).thenReturn(List.of());

        mockMvc.perform(get("/api/users/{username}/comments", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void getCommentsByUsernameHandlesException() throws Exception {
        String username = "userWithException";

        when(getAllCommentsUseCase.getCommentsByUsername(username)).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/api/users/{username}/comments", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void editCommentReturnsOk() throws Exception {
        EditCommentCommand editCommentCommand = TestObjectFactory.createDummyEditCommentCommand("1");

        doNothing().when(editCommentUseCase).editComment(any());

        mockMvc.perform(patch("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(editCommentCommand)))
                .andExpect(status().isOk());
    }

    @Test
    void editCommentHandlesNoSuchElementException() throws Exception {
        EditCommentCommand editCommentCommand = TestObjectFactory.createDummyEditCommentCommand("1");
        doThrow(new NoSuchElementException("Comment not found")).when(editCommentUseCase).editComment(any());

        mockMvc.perform(patch("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(editCommentCommand)))
                .andExpect(status().isNotFound());
    }
}
