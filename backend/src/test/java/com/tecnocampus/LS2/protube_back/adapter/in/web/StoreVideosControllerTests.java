package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreVideoUseCase;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StoreVideosControllerTests {
    private MockMvc mockMvc;

    @Mock
    StoreVideoUseCase storeVideoUseCase;

    @InjectMocks
    StoreVideoController storeVideoController;

    @BeforeEach
    void setUp() throws Exception {
        try (var ignored = MockitoAnnotations.openMocks(this)) {
            mockMvc = MockMvcBuilders.standaloneSetup(storeVideoController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .build();
        }

    }

    @Test
    void storeVideoHandlesIllegalArgumentException() throws Exception {
        StoreVideoCommand storeVideoCommand = TestObjectFactory.createDummyStoreVideoCommand("1");
        doThrow(new IllegalArgumentException("Video already exists")).when(storeVideoUseCase).storeVideo(any());

        mockMvc.perform(post("/api/videos")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(storeVideoCommand)))
                .andExpect(content().string("Video already exists"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void storeVideoHandlesNoSuchElementException() throws Exception {
        StoreVideoCommand storeVideoCommand = TestObjectFactory.createDummyStoreVideoCommand("1");
        doThrow(new NoSuchElementException("User not found")).when(storeVideoUseCase).storeVideo(any());

        mockMvc.perform(post("/api/videos")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(storeVideoCommand)))
                .andExpect(content().string("User not found"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void storeVideoReturnsCreated() throws Exception {
        StoreVideoCommand storeVideoCommand = TestObjectFactory.createDummyStoreVideoCommand("1");

        mockMvc.perform(post("/api/videos")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(storeVideoCommand)))
                .andExpect(status().isCreated());
    }
}
