package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.domain.model.VideoTitle;
import com.tecnocampus.LS2.protube_back.port.out.GetVideosPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class GetVideosServiceTests {

    @Mock
    private GetVideosPort getVideosPort;

    @InjectMocks
    private GetVideosService getVideosService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllVideosReturnsListOfVideos() {
        List<Video> videos = List.of(new Video("id 1", "Video 1"), new Video("id 2", "Video 2"));
        when(getVideosPort.getAllVideos()).thenReturn(videos);

        List<VideoTitle> result = getVideosService.getAllVideos();

        assertEquals(2, result.size());
        assertEquals("Video 1", result.get(0).title());
        assertEquals("Video 2", result.get(1).title());
    }

    @Test
    void getAllVideosReturnsEmptyListWhenNoVideos() {
        when(getVideosPort.getAllVideos()).thenReturn(List.of());

        List<VideoTitle> result = getVideosService.getAllVideos();

        assertTrue(result.isEmpty());
    }

    @Test
    void getAllVideosHandlesNullVideos() {
        when(getVideosPort.getAllVideos()).thenReturn(null);

        List<VideoTitle> result = getVideosService.getAllVideos();

        assertTrue(result.isEmpty());
    }
}
