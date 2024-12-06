package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.command.GetVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.SearchVideoResultCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VideoRestControllerTests {
    private MockMvc mockMvc;

    @Mock
    private StoreVideoUseCase storeVideoUseCase;

    @Mock
    private DeleteVideoUseCase deleteVideoUseCase;

    @Mock
    private GetAllVideosUseCase getAllVideosUseCase;

    @Mock
    private GetVideoByIdUseCase getVideoByIdUseCase;

    @Mock
    private GetAllVideosByUsernameUseCase getAllVideosByUsernameUseCase;

    @Mock
    private SearchVideosUseCase searchVideosUseCase;

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
        MockMultipartFile file = new MockMultipartFile("file", "video.mp4", MediaType.MULTIPART_FORM_DATA_VALUE, "video content".getBytes());
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "thumbnail.png", MediaType.MULTIPART_FORM_DATA_VALUE, "thumbnail content".getBytes());
        StoreVideoCommand storeVideoCommand = TestObjectFactory.createDummyStoreVideoCommand("1");
        doThrow(new IllegalArgumentException("Video already exists")).when(storeVideoUseCase).storeVideoWithFiles(any(), any(), any());

        mockMvc.perform(multipart("/api/videos")
                        .file(file)
                        .file(thumbnail)
                        .param("storeVideoCommand", new ObjectMapper().writeValueAsString(storeVideoCommand)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void storeVideoWithFilesReturnsCreated() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "video.mp4", MediaType.MULTIPART_FORM_DATA_VALUE, "video content".getBytes());
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "thumbnail.png", MediaType.MULTIPART_FORM_DATA_VALUE, "thumbnail content".getBytes());
        StoreVideoCommand storeVideoCommand = TestObjectFactory.createDummyStoreVideoCommand("1");

        MockMultipartFile storeVideoCommandPart = new MockMultipartFile("storeVideoCommand", "", MediaType.APPLICATION_JSON_VALUE, new ObjectMapper().writeValueAsString(storeVideoCommand).getBytes());

        mockMvc.perform(multipart("/api/videos")
                        .file(file)
                        .file(thumbnail)
                        .file(storeVideoCommandPart))
                .andExpect(status().isCreated());
    }

    @Test
    void storeVideoHandlesNoSuchElementException() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "video.mp4", MediaType.MULTIPART_FORM_DATA_VALUE, "video content".getBytes());
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "thumbnail.png", MediaType.MULTIPART_FORM_DATA_VALUE, "thumbnail content".getBytes());
        StoreVideoCommand storeVideoCommand = TestObjectFactory.createDummyStoreVideoCommand("1");

        MockMultipartFile storeVideoCommandPart = new MockMultipartFile("storeVideoCommand", "", MediaType.APPLICATION_JSON_VALUE, new ObjectMapper().writeValueAsString(storeVideoCommand).getBytes());
        doThrow(new NoSuchElementException("User not found")).when(storeVideoUseCase).storeVideoWithFiles(any(), any(), any());

        mockMvc.perform(multipart("/api/videos")
                        .file(file)
                        .file(thumbnail)
                        .file(storeVideoCommandPart))
                .andExpect(status().isNotFound());
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
    @Test
    void getVideoByIdReturnsVideo() throws Exception {
        String videoId = "1";
        GetVideoCommand videoCommand = TestObjectFactory.createDummyGetVideoCommand(videoId);
        when(getVideoByIdUseCase.getVideoById(videoId)).thenReturn(videoCommand);

        mockMvc.perform(get("/api/videos/{id}", videoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(videoCommand)));
    }

    @Test
    void getVideoByIdReturnsNotFoundForIncorrectId() throws Exception {
        String incorrectVideoId = "999";
        when(getVideoByIdUseCase.getVideoById(incorrectVideoId))
                .thenThrow(new NoSuchElementException("Video not found"));

        mockMvc.perform(get("/api/videos/{id}", incorrectVideoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteVideoReturnsOk() throws Exception {
        String videoId = "1";

        doNothing().when(deleteVideoUseCase).deleteVideo(videoId);

        mockMvc.perform(delete("/api/videos/{videoId}", videoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(deleteVideoUseCase, times(1)).deleteVideo(videoId);
    }

    @Test
    void deleteVideoReturnsNotFoundWhenVideoNotFound() throws Exception {
        String videoId = "nonExistentId";

        doThrow(new NoSuchElementException("Video not found")).when(deleteVideoUseCase).deleteVideo(videoId);

        mockMvc.perform(delete("/api/videos/{videoId}", videoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(deleteVideoUseCase, times(1)).deleteVideo(videoId);
    }

    @Test
    void getAllVideosByUsernameReturnsListOfVideos() throws Exception {
        String username = "testUser";
        Video video1 = TestObjectFactory.createDummyVideo("1");
        Video video2 = TestObjectFactory.createDummyVideo("2");
        List<Video> videos = List.of(video1, video2);

        List<GetVideoCommand> videoCommands = videos.stream()
                .map(video -> GetVideoCommand.from(video, List.of(), List.of(), List.of()))
                .collect(Collectors.toList());
        when(getAllVideosByUsernameUseCase.getAllVideosByUsername(username)).thenReturn(videoCommands);

        mockMvc.perform(get("/api/users/{username}/videos", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(videoCommands)));
    }

    @Test
    void getAllVideosByUsernameReturnsEmptyListWhenNoVideos() throws Exception {
        String username = "testUser";
        when(getAllVideosByUsernameUseCase.getAllVideosByUsername(username)).thenReturn(List.of());

        mockMvc.perform(get("/api/users/{username}/videos", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void getAllVideosByUsernameHandlesException() throws Exception {
        String username = "testUser";
        when(getAllVideosByUsernameUseCase.getAllVideosByUsername(username)).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/api/users/{username}/videos", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void searchVideosReturnsListOfVideos() throws Exception {
        String searchText = "My heart";
        Video video1 = TestObjectFactory.createDummyVideo("1");
        Video video2 = TestObjectFactory.createDummyVideo("2");
        List<SearchVideoResultCommand> searchResults = List.of(
                SearchVideoResultCommand.from(video1),
                SearchVideoResultCommand.from(video2)
        );

        when(searchVideosUseCase.searchVideos(searchText)).thenReturn(searchResults);

        mockMvc.perform(get("/api/videos/search/{text}", searchText)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(searchResults)));
    }

    @Test
    void searchVideosReturnsEmptyListWhenNoVideos() throws Exception {
        String searchText = "NonExistent";
        when(searchVideosUseCase.searchVideos(searchText)).thenReturn(List.of());

        mockMvc.perform(get("/api/videos/search/{text}", searchText)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }
}
