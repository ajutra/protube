package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreCommentUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StoreCommentControllerTests {

    private MockMvc mockMvc;

    @Mock
    StoreCommentUseCase storeCommentUseCase;

    @InjectMocks
    StoreCommentController storeCommentController;

    @BeforeEach
    void setUp() throws Exception {
        try (var ignored = MockitoAnnotations.openMocks(this)) {
            mockMvc = MockMvcBuilders.standaloneSetup(storeCommentController)
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
}
