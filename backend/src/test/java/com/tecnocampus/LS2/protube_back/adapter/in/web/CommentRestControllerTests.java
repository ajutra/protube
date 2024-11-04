package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.port.in.command.GetCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllCommentsByVideoUseCase;
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
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentRestControllerTests {

    private MockMvc mockMvc;

    @Mock
    StoreCommentUseCase storeCommentUseCase;

    @InjectMocks
    CommentRestController commentRestController;

    @Mock
    private GetAllCommentsByVideoUseCase getAllCommentsByVideoUseCase;

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
    public void testGetCommentsByVideoId() {
        String videoId = "testVideoId";
        List<GetCommentCommand> mockComments = TestObjectFactory.createDummyGetCommentCommandList(videoId, 3);
        when(getAllCommentsByVideoUseCase.getAllCommentsByVideo(videoId)).thenReturn(mockComments);

        Map<String, Object> response = commentRestController.getCommentsByVideoId(videoId);

        assertEquals(3, ((Map<?, ?>) response.get("comments")).size());
        assertEquals("Comment Text 1", ((Map<?, ?>) ((Map<?, ?>) response.get("comments")).get("0")).get("text"));
        assertEquals("Username1", ((Map<?, ?>) ((Map<?, ?>) response.get("comments")).get("0")).get("author"));
        assertEquals("Comment Text 2", ((Map<?, ?>) ((Map<?, ?>) response.get("comments")).get("1")).get("text"));
        assertEquals("Username2", ((Map<?, ?>) ((Map<?, ?>) response.get("comments")).get("1")).get("author"));
        assertEquals("Comment Text 3", ((Map<?, ?>) ((Map<?, ?>) response.get("comments")).get("2")).get("text"));
        assertEquals("Username3", ((Map<?, ?>) ((Map<?, ?>) response.get("comments")).get("2")).get("author"));

        verify(getAllCommentsByVideoUseCase).getAllCommentsByVideo(videoId);
    }
}
