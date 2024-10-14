package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpa_entities.*;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
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
        VideoJpaEntity videoJpaEntity1 = new VideoJpaEntity(
                "1",
                1920,
                1080,
                300,
                "Title 1",
                "Description 1",
                new UserJpaEntity("user_id1", "username1"),
                new HashSet<>(),
                new HashSet<>());

        VideoJpaEntity videoJpaEntity2 = new VideoJpaEntity(
                "2",
                1920,
                1080,
                300,
                "Title 2",
                "Description 2",
                new UserJpaEntity("user_id2", "username2"),
                new HashSet<>(),
                new HashSet<>());

        Video videoExpected1 = new Video(
                "1",
                1920,
                1080,
                300,
                "Title 1",
                "Description 1",
                "user_id1");

        Video videoExpected2 = new Video(
                "2",
                1920,
                1080,
                300,
                "Title 2",
                "Description 2",
                "user_id2");


        List<VideoJpaEntity> videoEntities = List.of(videoJpaEntity1, videoJpaEntity2);
        when(videoRepository.findAll()).thenReturn(videoEntities);
        when(videoMapper.toDomain(videoJpaEntity1)).thenReturn(videoExpected1);
        when(videoMapper.toDomain(videoJpaEntity2)).thenReturn(videoExpected2);

        List<Video> result = videosPersistenceAdapter.getAllVideos();

        assertEquals(2, result.size());

        assertEquals(videoExpected1.id(), result.getFirst().id());
        assertEquals(videoExpected1.width(), result.getFirst().width());
        assertEquals(videoExpected1.height(), result.getFirst().height());
        assertEquals(videoExpected1.duration(), result.getFirst().duration());
        assertEquals(videoExpected1.title(), result.getFirst().title());
        assertEquals(videoExpected1.description(), result.getFirst().description());
        assertEquals(videoExpected1.userId(), result.getFirst().userId());

        assertEquals(videoExpected2.id(), result.getLast().id());
        assertEquals(videoExpected2.width(), result.getLast().width());
        assertEquals(videoExpected2.height(), result.getLast().height());
        assertEquals(videoExpected2.duration(), result.getLast().duration());
        assertEquals(videoExpected2.title(), result.getLast().title());
        assertEquals(videoExpected2.description(), result.getLast().description());
        assertEquals(videoExpected2.userId(), result.getLast().userId());
    }

    @Test
    void getAllVideosReturnsEmptyListWhenNoVideos() {
        when(videoRepository.findAll()).thenReturn(List.of());

        List<Video> result = videosPersistenceAdapter.getAllVideos();

        assertTrue(result.isEmpty());
    }
}