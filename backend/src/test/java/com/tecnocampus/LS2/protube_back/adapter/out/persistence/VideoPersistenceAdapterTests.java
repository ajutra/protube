package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.*;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.VideoMapper;
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

public class VideoPersistenceAdapterTests {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private VideoMapper videoMapper;

    @InjectMocks
    private VideoPersistenceAdapter videoPersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllVideosReturnsListOfVideos() {
        VideoJpaEntity videoJpaEntity1 = TestObjectFactory.createDummyVideoJpaEntity("1");
        VideoJpaEntity videoJpaEntity2 = TestObjectFactory.createDummyVideoJpaEntity("2");
        Video videoExpected1 = TestObjectFactory.createDummyVideo("1");
        Video videoExpected2 = TestObjectFactory.createDummyVideo("2");
        List<VideoJpaEntity> videoEntities = List.of(videoJpaEntity1, videoJpaEntity2);

        when(videoRepository.findAll()).thenReturn(videoEntities);
        when(videoMapper.toDomain(videoJpaEntity1)).thenReturn(videoExpected1);
        when(videoMapper.toDomain(videoJpaEntity2)).thenReturn(videoExpected2);

        List<Video> result = videoPersistenceAdapter.getAllVideos();

        assertEquals(2, result.size());

        assertEquals(videoExpected1.id(), result.getFirst().id());
        assertEquals(videoExpected1.width(), result.getFirst().width());
        assertEquals(videoExpected1.height(), result.getFirst().height());
        assertEquals(videoExpected1.duration(), result.getFirst().duration());
        assertEquals(videoExpected1.title(), result.getFirst().title());
        assertEquals(videoExpected1.description(), result.getFirst().description());
        assertEquals(videoExpected1.username(), result.getFirst().username());
        assertEquals(videoExpected1.videoFileName(), result.getFirst().videoFileName());
        assertEquals(videoExpected1.thumbnailFileName(), result.getFirst().thumbnailFileName());

        assertEquals(videoExpected2.id(), result.getLast().id());
        assertEquals(videoExpected2.width(), result.getLast().width());
        assertEquals(videoExpected2.height(), result.getLast().height());
        assertEquals(videoExpected2.duration(), result.getLast().duration());
        assertEquals(videoExpected2.title(), result.getLast().title());
        assertEquals(videoExpected2.description(), result.getLast().description());
        assertEquals(videoExpected2.username(), result.getLast().username());
        assertEquals(videoExpected2.videoFileName(), result.getLast().videoFileName());
        assertEquals(videoExpected2.thumbnailFileName(), result.getLast().thumbnailFileName());
    }

    @Test
    void getAllVideosReturnsEmptyListWhenNoVideos() {
        when(videoRepository.findAll()).thenReturn(List.of());

        List<Video> result = videoPersistenceAdapter.getAllVideos();

        assertTrue(result.isEmpty());
    }
}