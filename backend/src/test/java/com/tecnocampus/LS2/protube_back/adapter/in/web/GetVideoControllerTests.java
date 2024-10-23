package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.command.GetVideoCommand;
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
import java.util.stream.Collectors;

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
    void getAllVideosReturnsListOfVideosNames() throws Exception {
        Video video1 = TestObjectFactory.createDummyVideo("1");
        Video video2 = TestObjectFactory.createDummyVideo("2");
        List<Video> videos = List.of(video1, video2);

        List<GetVideoCommand> videoCommands = videos.stream().map(video -> GetVideoCommand.from(video, List.of(), List.of(), List.of())).collect(Collectors.toList());
        when(getAllVideosUseCase.getAllVideos()).thenReturn(videoCommands);

        mockMvc.perform(get("/api/videos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                 [
                                   {
                                     "videoId": "1",
                                     "width": 1920,
                                     "height": 1080,
                                     "duration": 300,
                                     "title": "Title 1",
                                     "username": "Username 1",
                                     "videoFileName": "Video File Name 1",
                                     "thumbnailFileName": "Thumbnail File Name 1",
                                     "meta": {
                                       "description": "Description 1",
                                       "categories": [],
                                       "tags": [],
                                       "comments": []
                                     }
                                   },
                                   {
                                     "videoId": "2",
                                     "width": 1920,
                                     "height": 1080,
                                     "duration": 300,
                                     "title": "Title 2",
                                     "username": "Username 2",
                                     "videoFileName": "Video File Name 2",
                                     "thumbnailFileName": "Thumbnail File Name 2",
                                     "meta": {
                                       "description": "Description 2",
                                       "categories": [],
                                       "tags": [],
                                       "comments": []
                                     }
                                   }
                                 ]
                         """));
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