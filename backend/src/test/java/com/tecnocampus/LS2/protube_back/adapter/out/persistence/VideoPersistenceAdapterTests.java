package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.VideoJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.VideoMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.TagMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.CategoryMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.repository.VideoRepository;
import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VideoPersistenceAdapterTests {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private UserPersistenceAdapter userPersistenceAdapter;

    @Mock
    private VideoMapper videoMapper;

    @Mock
    private TagMapper tagMapper;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private VideoPersistenceAdapter videoPersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllVideosReturnsListOfVideos() {
        List<VideoJpaEntity> videoJpaEntities = List.of(TestObjectFactory.createDummyVideoJpaEntity("video1"));
        List<Video> videos = List.of(TestObjectFactory.createDummyVideo("video1"));

        when(videoRepository.findAll()).thenReturn(videoJpaEntities);
        when(videoMapper.toDomain(videoJpaEntities.getFirst())).thenReturn(videos.getFirst());

        List<Video> result = videoPersistenceAdapter.getAllVideos();

        assertEquals(videos, result);
    }

    @Test
    void getVideoByTitleAndUsernameWhenVideoExists() {
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity("video1");
        Video video = TestObjectFactory.createDummyVideo("video1");

        when(videoRepository.findByTitleAndUserUsername("video1", "user1")).thenReturn(Optional.of(videoJpaEntity));
        when(videoMapper.toDomain(videoJpaEntity)).thenReturn(video);

        Video result = videoPersistenceAdapter.getVideoByTitleAndUsername("video1", "user1");

        assertEquals(video, result);
    }

    @Test
    void getVideoByTitleAndUsernameWhenVideoDoesNotExist() {
        when(videoRepository.findByTitleAndUserUsername("video1", "user1")).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> videoPersistenceAdapter.getVideoByTitleAndUsername("video1", "user1"));

        assertEquals("Video not found", exception.getMessage());
    }

    @Test
    void storeVideoWhenUserExists() {
        Video video = TestObjectFactory.createDummyVideo("video1");
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity("video1");
        Set<Tag> tags = Set.of(TestObjectFactory.createDummyTag("tag1"));
        Set<Category> categories = Set.of(TestObjectFactory.createDummyCategory("category1"));

        when(userPersistenceAdapter.findByUsername(any())).thenReturn(TestObjectFactory.createDummyUserJpaEntity("user1"));
        when(videoMapper.toJpaEntity(any(), any(), any(), any())).thenReturn(videoJpaEntity);
        when(tagMapper.toJpaEntity(any())).thenReturn(
                TestObjectFactory.createDummyTagJpaEntity("tag1"));
        when(categoryMapper.toJpaEntity(any())).thenReturn(
                TestObjectFactory.createDummyCategoryJpaEntity("category1"));

        videoPersistenceAdapter.storeVideo(video, tags, categories);

        verify(videoRepository).save(videoJpaEntity);
    }

    @Test
    void storeAndGetVideoWhenUserExists() {
        Video video = TestObjectFactory.createDummyVideo("video1");
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity("video1");
        Set<Tag> tags = Set.of(TestObjectFactory.createDummyTag("tag1"));
        Set<Category> categories = Set.of(TestObjectFactory.createDummyCategory("category1"));
        UserJpaEntity userJpaEntity = TestObjectFactory.createDummyUserJpaEntity("user1");

        when(userPersistenceAdapter.findByUsername(any())).thenReturn(userJpaEntity);
        when(videoMapper.toJpaEntity(any(), any(), any(), any())).thenReturn(videoJpaEntity);
        when(videoMapper.toDomain(videoJpaEntity)).thenReturn(video);
        when(tagMapper.toJpaEntity(any())).thenReturn(
               TestObjectFactory.createDummyTagJpaEntity("tag1"));
        when(categoryMapper.toJpaEntity(any())).thenReturn(
                TestObjectFactory.createDummyCategoryJpaEntity("category1"));

        Video result = videoPersistenceAdapter.storeAndGetVideo(video, tags, categories);

        assertEquals(video, result);
        verify(videoRepository).save(videoJpaEntity);
    }

    @Test
    void checkIfVideoExistsWhenVideoExists() {
        String existingVideoId = "existingVideoId";
        when(videoRepository.findById(existingVideoId)).thenReturn(Optional.of(new VideoJpaEntity()));

        assertDoesNotThrow(() -> videoPersistenceAdapter.checkIfVideoExists(existingVideoId));
    }

    @Test
    void checkIfVideoExistsWhenVideoDoesNotExist() {
        String nonExistingVideoId = "nonExistingVideoId";

        when(videoRepository.findById(nonExistingVideoId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> videoPersistenceAdapter.checkIfVideoExists(nonExistingVideoId));

        assertEquals("Video not found with ID: " + nonExistingVideoId, exception.getMessage());
    }
}