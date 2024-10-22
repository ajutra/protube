package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllVideosUseCase;
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

public class GetVideoControllerTests {

    private MockMvc mockMvc;

    @Mock
    private GetAllVideosUseCase getAllVideosUseCase;

    @InjectMocks
    private GetVideoController getVideoController;

    @BeforeEach
    void setUp() throws Exception {
        try (var ignored = MockitoAnnotations.openMocks(this)) {
            mockMvc = MockMvcBuilders.standaloneSetup(getVideoController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .build();
        }
    }

    @Test
    void getAllVideosNamesReturnsListOfVideosNames() throws Exception {
        List<Video> videos = List.of(
                new Video("1",1920,1080,300,"Sample Video 1","First test video","testUser1","Sample Video 1testUser1.mp4","Sample Video 1testUser1.webp"),
                new Video("2",1280,720,250,"Sample Video 2","","testUser2","Sample Video 2testUser2.mp4","Sample Video 2testUser2.webp"));
        when(getAllVideosUseCase.getAllVideos()).thenReturn(videos);

        mockMvc.perform(get("/videos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":\"1\",\"width\":1920,\"height\":1080,\"duration\":300,\"title\":\"Sample Video 1\",\"description\":\"First test video\",\"username\":\"testUser1\",\"videoFileName\":\"Sample Video 1testUser1.mp4\",\"thumbnailFileName\":\"Sample Video 1testUser1.webp\"},{\"id\":\"2\",\"width\":1280,\"height\":720,\"duration\":250,\"title\":\"Sample Video 2\",\"description\":\"\",\"username\":\"testUser2\",\"videoFileName\":\"Sample Video 2testUser2.mp4\",\"thumbnailFileName\":\"Sample Video 2testUser2.webp\"}]"));
    }

    @Test
    void getAllVideosNamesReturnsEmptyListWhenNoVideos() throws Exception {
        when(getAllVideosUseCase.getAllVideos()).thenReturn(List.of());

        mockMvc.perform(get("/videos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void getAllVideosNamesHandlesException() throws Exception {
        when(getAllVideosUseCase.getAllVideos()).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/videos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}