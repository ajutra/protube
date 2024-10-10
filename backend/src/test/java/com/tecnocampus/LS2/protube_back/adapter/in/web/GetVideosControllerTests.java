package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.domain.model.VideoTitle;
import com.tecnocampus.LS2.protube_back.port.in.GetVideosUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetVideosControllerTests {

    private MockMvc mockMvc;

    @Mock
    private GetVideosUseCase getVideosUseCase;

    @InjectMocks
    private GetVideosController getVideosController;

    @BeforeEach
    void setUp() throws Exception {
        try (var ignored = MockitoAnnotations.openMocks(this)) {
            mockMvc = MockMvcBuilders.standaloneSetup(getVideosController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .build();
        }
    }

    @Test
    void getAllVideosReturnsListOfVideos() throws Exception {
        List<VideoTitle> videos = List.of(new VideoTitle("Video 1"), new VideoTitle("Video 2"));
        when(getVideosUseCase.getAllVideos()).thenReturn(videos);

        mockMvc.perform(get("/videos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"title\":\"Video 1\"},{\"title\":\"Video 2\"}]"));
    }

    @Test
    void getAllVideosReturnsEmptyListWhenNoVideos() throws Exception {
        when(getVideosUseCase.getAllVideos()).thenReturn(List.of());

        mockMvc.perform(get("/videos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void getAllVideosHandlesException() throws Exception {
        when(getVideosUseCase.getAllVideos()).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/videos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}