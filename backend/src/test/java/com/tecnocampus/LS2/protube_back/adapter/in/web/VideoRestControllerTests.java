package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.command.GetVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllVideosUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreVideoUseCase;
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
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VideoRestControllerTests {
    private MockMvc mockMvc;

    @Mock
    StoreVideoUseCase storeVideoUseCase;

    @Mock
    private GetAllVideosUseCase getAllVideosUseCase;

    @InjectMocks
    VideoRestController videoRestController;

    @BeforeEach
    void setUp() throws Exception {
        try (var ignored = MockitoAnnotations.openMocks(this)) {
            mockMvc = MockMvcBuilders.standaloneSetup(videoRestController)
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
                .andExpect(status().isBadRequest());
    }

    @Test
    void storeVideoHandlesNoSuchElementException() throws Exception {
        StoreVideoCommand storeVideoCommand = TestObjectFactory.createDummyStoreVideoCommand("1");
        doThrow(new NoSuchElementException("User not found")).when(storeVideoUseCase).storeVideo(any());

        mockMvc.perform(post("/api/videos")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(storeVideoCommand)))
                .andExpect(status().isNotFound());
    }

    @Test
    void storeVideoReturnsCreated() throws Exception {
        StoreVideoCommand storeVideoCommand = TestObjectFactory.createDummyStoreVideoCommand("1");

        mockMvc.perform(post("/api/videos")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(storeVideoCommand)))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllVideosReturnsListOfVideosNames() throws Exception {
        Video video1 = TestObjectFactory.createDummyVideo("1");
        Video video2 = TestObjectFactory.createDummyVideo("2");
        List<Video> videos = List.of(video1, video2);

        List<GetVideoCommand> videoCommands = videos.stream().map(video -> GetVideoCommand.from(video, List.of(), List.of(), List.of())).collect(Collectors.toList());
        when(getAllVideosUseCase.getAllVideos()).thenReturn(videoCommands);

        mockMvc.perform(get("/api/videos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(videoCommands)));
    }

    @Test
    void getAllVideosReturnsEmptyListWhenNoVideos() throws Exception {
        when(getAllVideosUseCase.getAllVideos()).thenReturn(List.of());

        mockMvc.perform(get("/api/videos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void getAllVideosHandlesException() throws Exception {
        when(getAllVideosUseCase.getAllVideos()).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/api/videos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
