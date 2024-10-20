package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
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
        Video video1 = TestObjectFactory.createDummyVideo("1");
        Video video2 = TestObjectFactory.createDummyVideo("2");
        List<Video> videos = List.of(video1, video2);

        when(getVideosPort.getAllVideos()).thenReturn(videos);

        List<VideoTitle> videoTitles = getVideosService.getAllVideos();

        assertEquals(2, videoTitles.size());
        assertEquals(videos.getFirst().title(), videoTitles.getFirst().title());
        assertEquals(videos.getLast().title(), videoTitles.getLast().title());
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
