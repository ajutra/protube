package com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo.document.VideoDocument;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo.mapper.MongoVideoMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo.repository.MongoVideoRepository;
import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.TextQuery;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class MongoVideoPersistenceAdapterTests {

    @Mock
    private MongoVideoRepository mongoVideoRepository;

    @Mock
    private MongoVideoMapper mongoVideoMapper;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private MongoVideoPersistenceAdapter mongoVideoPersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void storeAndGetVideoStoresVideoSuccessfully() {
        Video video = new Video();
        Set<Tag> tags = Set.of(new Tag("tag1"));
        Set<Category> categories = Set.of(new Category("category1"));
        VideoDocument videoDocument = new VideoDocument("1", "Test Title", "TestUser", Set.of("tag1"), Set.of("category1"));

        when(mongoVideoMapper.toDocument(video, tags, categories)).thenReturn(videoDocument);
        when(mongoVideoRepository.findVideoDocumentByTitleEqualsAndUsernameEquals("Test Title", "TestUser"))
                .thenReturn(Optional.empty());
        when(mongoVideoRepository.save(videoDocument)).thenReturn(videoDocument);
        when(mongoVideoMapper.toDomain(videoDocument)).thenReturn(video);

        Video result = mongoVideoPersistenceAdapter.storeAndGetVideo(video, tags, categories);

        assertEquals(video, result);
        verify(mongoVideoRepository).save(videoDocument);
    }

    @Test
    void storeVideoStoresVideoSuccessfully() {
        Video video = new Video();
        Set<Tag> tags = Set.of(new Tag("tag1"));
        Set<Category> categories = Set.of(new Category("category1"));
        VideoDocument videoDocument = new VideoDocument("1", "Test Title", "TestUser", Set.of("tag1"), Set.of("category1"));

        when(mongoVideoMapper.toDocument(video, tags, categories)).thenReturn(videoDocument);
        when(mongoVideoRepository.findVideoDocumentByTitleEqualsAndUsernameEquals("Test Title", "TestUser"))
                .thenReturn(Optional.empty());
        when(mongoVideoRepository.save(videoDocument)).thenReturn(videoDocument);
        when(mongoVideoMapper.toDomain(videoDocument)).thenReturn(video);

        mongoVideoPersistenceAdapter.storeVideo(video, tags, categories);

        verify(mongoVideoRepository).save(videoDocument);
    }

    @Test
    void updatesExistingVideo() {
        Video video = new Video();
        Set<Tag> tags = Set.of(new Tag("tag1"));
        Set<Category> categories = Set.of(new Category("category1"));
        VideoDocument videoDocument = new VideoDocument("1", "Test Title", "TestUser", Set.of("tag1"), Set.of("category1"));
        VideoDocument existingDocument = new VideoDocument("1", "Test Title", "TestUser", Set.of("tag1"), Set.of("category1"));

        when(mongoVideoMapper.toDocument(video, tags, categories)).thenReturn(videoDocument);
        when(mongoVideoRepository.findVideoDocumentByTitleEqualsAndUsernameEquals("Test Title", "TestUser"))
                .thenReturn(Optional.of(existingDocument));
        when(mongoVideoRepository.save(videoDocument)).thenReturn(videoDocument);
        when(mongoVideoMapper.toDomain(videoDocument)).thenReturn(video);

        Video result = mongoVideoPersistenceAdapter.storeAndGetVideo(video, tags, categories);

        assertEquals(video, result);
        verify(mongoVideoRepository).delete(existingDocument);
        verify(mongoVideoRepository).save(videoDocument);
    }

    @Test
    void searchesVideosByText() {
        String searchText = "Test";
        VideoDocument videoDocument = new VideoDocument("1", "Test Title", "TestUser", Set.of("tag1"), Set.of("category1"));
        Video video = new Video();

        when(mongoTemplate.find(any(TextQuery.class), eq(VideoDocument.class), eq("video")))
                .thenReturn(List.of(videoDocument));
        when(mongoVideoMapper.toDomain(videoDocument)).thenReturn(video);

        List<Video> result = mongoVideoPersistenceAdapter.searchVideos(searchText);

        assertEquals(List.of(video), result);
    }

    @Test
    void deletesVideoById() {
        String videoId = "1";

        mongoVideoPersistenceAdapter.deleteVideo(videoId);

        verify(mongoVideoRepository).deleteById(videoId);
    }

    @Test
    void searchVideosReturnsEmptyListWhenNoMatches() {
        String searchText = "NonExistent";
        when(mongoTemplate.find(any(TextQuery.class), eq(VideoDocument.class), eq("video")))
                .thenReturn(List.of());

        List<Video> result = mongoVideoPersistenceAdapter.searchVideos(searchText);

        assertTrue(result.isEmpty());
    }

    @Test
    void searchVideosHandlesException() {
        String searchText = "ErrorSearch";
        when(mongoTemplate.find(any(TextQuery.class), eq(VideoDocument.class), eq("video")))
                .thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> mongoVideoPersistenceAdapter.searchVideos(searchText));
    }

    @Test
    void editVideoUpdatesExistingVideo() {
        Video video = new Video();
        video.setId("1");
        video.setTitle("Updated Title");
        VideoDocument videoDocument = new VideoDocument("1", "Original Title", "TestUser", Set.of("tag1"), Set.of("category1"));

        when(mongoVideoRepository.findById("1")).thenReturn(Optional.of(videoDocument));

        mongoVideoPersistenceAdapter.editVideo(video);

        verify(mongoVideoRepository).save(videoDocument);
        assertEquals("Updated Title", videoDocument.getTitle());
    }

    @Test
    void editVideoThrowsExceptionWhenVideoNotFound() {
        Video video = new Video();
        video.setId("1");

        when(mongoVideoRepository.findById("1")).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> mongoVideoPersistenceAdapter.editVideo(video));

        assertEquals("Video with id 1 not found on MongoDB", exception.getMessage());
    }
}