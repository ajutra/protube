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
    private GetAllVideosNamesService getVideosService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllVideosReturnsListOfVideos() {
        Video video1 = new Video(
                "1",
                1920,
                1080,
                300,
                "Title 1",
                "Description 1",
                "user_id1",
                "videoFileName1",
                "thumbnailFileName1");

        Video video2 = new Video(
                "2",
                1920,
                1080,
                300,
                "Title 2",
                "Description 2",
                "user_id2",
                "videoFileName2",
                "thumbnailFileName2");

        List<Video> videos = List.of(video1, video2);
        when(getVideosPort.getAllVideos()).thenReturn(videos);

        List<VideoTitle> result = getVideosService.getAllVideos();

        assertEquals(2, result.size());
        assertEquals("Title 1", result.get(0).title());
        assertEquals("Title 2", result.get(1).title());
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
