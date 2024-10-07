package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.domain.model.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class VideosPersistenceAdapterTests {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private VideoMapper videoMapper;

    @InjectMocks
    private VideosPersistenceAdapter videosPersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllVideosReturnsListOfVideos() {
        List<VideoJpaEntity> videoEntities = List.of(new VideoJpaEntity("1", "Video 1"), new VideoJpaEntity("2", "Video 2"));
        when(videoRepository.findAll()).thenReturn(videoEntities);
        when(videoMapper.toDomain(videoEntities.get(0))).thenReturn(new Video("1", "Video 1"));
        when(videoMapper.toDomain(videoEntities.get(1))).thenReturn(new Video("2", "Video 2"));

        List<Video> result = videosPersistenceAdapter.getAllVideos();

        assertEquals(2, result.size());
        assertEquals("Video 1", result.get(0).title());
        assertEquals("Video 2", result.get(1).title());
    }

    @Test
    void getAllVideosReturnsEmptyListWhenNoVideos() {
        when(videoRepository.findAll()).thenReturn(List.of());

        List<Video> result = videosPersistenceAdapter.getAllVideos();

        assertTrue(result.isEmpty());
    }
}