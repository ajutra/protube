package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.CategoryJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.TagJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.VideoJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.VideoMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.TagMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.CategoryMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.repository.VideoRepository;
import com.tecnocampus.LS2.protube_back.domain.model.*;
import com.tecnocampus.LS2.protube_back.port.in.command.EditVideoCommand;
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
    private CommentPersistenceAdapter commentPersistenceAdapter;

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

    @Test
    void getAllVideosWithFields_returnsListOfVideoWithAllData() {
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity("1");

        videoJpaEntity.setTags(Set.of(
                TestObjectFactory.createDummyTagJpaEntity("tag1"),
                TestObjectFactory.createDummyTagJpaEntity("tag2")));

        videoJpaEntity.setCategories(Set.of(
                TestObjectFactory.createDummyCategoryJpaEntity("category1"),
                TestObjectFactory.createDummyCategoryJpaEntity("category2")));

        Video video = TestObjectFactory.createDummyVideo("1");
        List<Tag> tags = List.of(
                TestObjectFactory.createDummyTag("tag1"),
                TestObjectFactory.createDummyTag("tag2"));

        List<Category> categories = List.of(
                TestObjectFactory.createDummyCategory("category1"),
                TestObjectFactory.createDummyCategory("category2"));

        List<Comment> comments = List.of(
                TestObjectFactory.createDummyComment("comment1"),
                TestObjectFactory.createDummyComment("comment2"));

        when(videoRepository.findAll()).thenReturn(List.of(videoJpaEntity));
        when(videoMapper.toDomain(videoJpaEntity)).thenReturn(video);
        when(tagMapper.toDomain(any(TagJpaEntity.class))).thenReturn(tags.getFirst(), tags.getLast());
        when(categoryMapper.toDomain(any(CategoryJpaEntity.class))).thenReturn(categories.getFirst(), categories.getLast());
        when(commentPersistenceAdapter.getAllCommentsByVideo(videoJpaEntity)).thenReturn(comments);

        List<PlayerPageVideo> result = videoPersistenceAdapter.getAllVideosWithFields(
                Set.of(Field.TAGS, Field.CATEGORIES, Field.COMMENTS));

        assertEquals(1, result.size());
        PlayerPageVideo playerPageVideo = result.getFirst();
        assertEquals(video, playerPageVideo.video());
        assertEquals(tags.size(), playerPageVideo.tags().size());
        assertEquals(tags.getFirst(), playerPageVideo.tags().getFirst());
        assertEquals(tags.getLast(), playerPageVideo.tags().getLast());
        assertEquals(categories.size(), playerPageVideo.categories().size());
        assertEquals(categories.getFirst(), playerPageVideo.categories().getFirst());
        assertEquals(categories.getLast(), playerPageVideo.categories().getLast());
        assertEquals(comments.size(), playerPageVideo.comments().size());
        assertEquals(comments.getFirst(), playerPageVideo.comments().getFirst());
        assertEquals(comments.getLast(), playerPageVideo.comments().getLast());
    }

    @Test
    void getAllVideosWithFields_returnsVideosWithOnlyTags() {
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity("1");

        videoJpaEntity.setTags(Set.of(
                TestObjectFactory.createDummyTagJpaEntity("tag1"),
                TestObjectFactory.createDummyTagJpaEntity("tag2")));

        Video video = TestObjectFactory.createDummyVideo("1");
        List<Tag> tags = List.of(
                TestObjectFactory.createDummyTag("tag1"),
                TestObjectFactory.createDummyTag("tag2"));

        when(videoRepository.findAll()).thenReturn(List.of(videoJpaEntity));
        when(videoMapper.toDomain(videoJpaEntity)).thenReturn(video);
        when(tagMapper.toDomain(any(TagJpaEntity.class))).thenReturn(tags.getFirst(), tags.getLast());

        List<PlayerPageVideo> result = videoPersistenceAdapter.getAllVideosWithFields(Set.of(Field.TAGS));

        assertEquals(1, result.size());
        assertEquals(video, result.getFirst().video());
        assertEquals(tags, result.getFirst().tags());
        assertTrue(result.getFirst().categories().isEmpty());
        assertTrue(result.getFirst().comments().isEmpty());

        verify(categoryMapper, never()).toDomain(any(CategoryJpaEntity.class));
        verify(commentPersistenceAdapter, never()).getAllCommentsByVideo(any(VideoJpaEntity.class));
    }

    @Test
    void getAllVideosWithFields_returnsVideosWithOnlyCategories() {
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity("1");

        videoJpaEntity.setCategories(Set.of(
                TestObjectFactory.createDummyCategoryJpaEntity("category1"),
                TestObjectFactory.createDummyCategoryJpaEntity("category2")));

        Video video = TestObjectFactory.createDummyVideo("1");
        List<Category> categories = List.of(
                TestObjectFactory.createDummyCategory("category1"),
                TestObjectFactory.createDummyCategory("category2"));

        when(videoRepository.findAll()).thenReturn(List.of(videoJpaEntity));
        when(videoMapper.toDomain(videoJpaEntity)).thenReturn(video);
        when(categoryMapper.toDomain(any(CategoryJpaEntity.class))).thenReturn(categories.getFirst(), categories.getLast());

        List<PlayerPageVideo> result = videoPersistenceAdapter.getAllVideosWithFields(Set.of(Field.CATEGORIES));

        assertEquals(1, result.size());
        assertEquals(video, result.getFirst().video());
        assertEquals(categories, result.getFirst().categories());
        assertTrue(result.getFirst().tags().isEmpty());
        assertTrue(result.getFirst().comments().isEmpty());

        verify(tagMapper, never()).toDomain(any(TagJpaEntity.class));
        verify(commentPersistenceAdapter, never()).getAllCommentsByVideo(any(VideoJpaEntity.class));
    }

    @Test
    void getAllVideosWithFields_returnsVideosWithOnlyComments() {
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity("1");
        Video video = TestObjectFactory.createDummyVideo("1");

        List<Comment> comments = List.of(
                TestObjectFactory.createDummyComment("comment1"),
                TestObjectFactory.createDummyComment("comment2"));

        when(videoRepository.findAll()).thenReturn(List.of(videoJpaEntity));
        when(videoMapper.toDomain(videoJpaEntity)).thenReturn(video);
        when(commentPersistenceAdapter.getAllCommentsByVideo(videoJpaEntity)).thenReturn(comments);

        List<PlayerPageVideo> result = videoPersistenceAdapter.getAllVideosWithFields(Set.of(Field.COMMENTS));

        assertEquals(1, result.size());
        assertEquals(video, result.getFirst().video());
        assertTrue(result.getFirst().tags().isEmpty());
        assertTrue(result.getFirst().categories().isEmpty());
        assertEquals(comments, result.getFirst().comments());

        verify(tagMapper, never()).toDomain(any(TagJpaEntity.class));
        verify(categoryMapper, never()).toDomain(any(CategoryJpaEntity.class));
    }

    @Test
    void getAllVideosWithFields_returnsEmptyListWhenNoVideos() {
        when(videoRepository.findAll()).thenReturn(List.of());

        List<PlayerPageVideo> result = videoPersistenceAdapter.getAllVideosWithFields(Set.of());

        assertTrue(result.isEmpty());
    }
    @Test
    void getVideoWithFieldsByIdReturnsVideoWithAllFields() {
        String videoId = "1";
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity(videoId);

        videoJpaEntity.setTags(Set.of(
                TestObjectFactory.createDummyTagJpaEntity("tag1"),
                TestObjectFactory.createDummyTagJpaEntity("tag2")));

        videoJpaEntity.setCategories(Set.of(
                TestObjectFactory.createDummyCategoryJpaEntity("category1"),
                TestObjectFactory.createDummyCategoryJpaEntity("category2")));

        Video video = TestObjectFactory.createDummyVideo(videoId);
        List<Tag> tags = List.of(
                TestObjectFactory.createDummyTag("tag1"),
                TestObjectFactory.createDummyTag("tag2"));

        List<Category> categories = List.of(
                TestObjectFactory.createDummyCategory("category1"),
                TestObjectFactory.createDummyCategory("category2"));

        List<Comment> comments = List.of(
                TestObjectFactory.createDummyComment("comment1"),
                TestObjectFactory.createDummyComment("comment2"));

        when(videoRepository.findById(videoId)).thenReturn(Optional.of(videoJpaEntity));
        when(videoMapper.toDomain(videoJpaEntity)).thenReturn(video);
        when(tagMapper.toDomain(any(TagJpaEntity.class))).thenReturn(tags.get(0), tags.get(1));
        when(categoryMapper.toDomain(any(CategoryJpaEntity.class))).thenReturn(categories.get(0), categories.get(1));
        when(commentPersistenceAdapter.getAllCommentsByVideo(videoJpaEntity)).thenReturn(comments);

        PlayerPageVideo result = videoPersistenceAdapter.getVideoWithFieldsById(videoId, Set.of(Field.TAGS, Field.CATEGORIES, Field.COMMENTS));

        assertEquals(video, result.video());
        assertEquals(tags.size(), result.tags().size());
        assertEquals(tags.get(0), result.tags().get(0));
        assertEquals(tags.get(1), result.tags().get(1));
        assertEquals(categories.size(), result.categories().size());
        assertEquals(categories.get(0), result.categories().get(0));
        assertEquals(categories.get(1), result.categories().get(1));
        assertEquals(comments.size(), result.comments().size());
        assertEquals(comments.get(0), result.comments().get(0));
        assertEquals(comments.get(1), result.comments().get(1));
    }

    @Test
    void getVideoWithFieldsByIdThrowsExceptionWhenVideoNotFound() {
        String videoId = "999";
        when(videoRepository.findById(videoId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> videoPersistenceAdapter.getVideoWithFieldsById(videoId, Set.of(Field.TAGS, Field.CATEGORIES, Field.COMMENTS)));

        assertEquals("Video not found with ID: " + videoId, exception.getMessage());
    }

    @Test
    void deleteVideoSuccessfully() {
        String videoId = "1";
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity(videoId);

        when(videoRepository.findById(videoId)).thenReturn(Optional.of(videoJpaEntity));

        videoPersistenceAdapter.deleteVideo(videoId);

        verify(videoRepository, times(1)).deleteById(videoId);
    }

    @Test
    void deleteVideoThrowsExceptionWhenVideoNotFound() {
        String videoId = "nonExistentId";

        when(videoRepository.findById(videoId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> videoPersistenceAdapter.deleteVideo(videoId));

        verify(videoRepository, never()).deleteById(videoId);
    }

    @Test
    void getAllVideosWithFieldsByUsernameReturnsVideosWithAllFields() {
        String username = "testUser";
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity("1");

        videoJpaEntity.setTags(Set.of(
                TestObjectFactory.createDummyTagJpaEntity("tag1"),
                TestObjectFactory.createDummyTagJpaEntity("tag2")));

        videoJpaEntity.setCategories(Set.of(
                TestObjectFactory.createDummyCategoryJpaEntity("category1"),
                TestObjectFactory.createDummyCategoryJpaEntity("category2")));

        Video video = TestObjectFactory.createDummyVideo("1");
        List<Tag> tags = List.of(
                TestObjectFactory.createDummyTag("tag1"),
                TestObjectFactory.createDummyTag("tag2"));

        List<Category> categories = List.of(
                TestObjectFactory.createDummyCategory("category1"),
                TestObjectFactory.createDummyCategory("category2"));

        List<Comment> comments = List.of(
                TestObjectFactory.createDummyComment("comment1"),
                TestObjectFactory.createDummyComment("comment2"));

        when(videoRepository.findAllByUserUsername(username)).thenReturn(List.of(videoJpaEntity));
        when(videoMapper.toDomain(videoJpaEntity)).thenReturn(video);
        when(tagMapper.toDomain(any(TagJpaEntity.class))).thenReturn(tags.get(0), tags.get(1));
        when(categoryMapper.toDomain(any(CategoryJpaEntity.class))).thenReturn(categories.get(0), categories.get(1));
        when(commentPersistenceAdapter.getAllCommentsByVideo(videoJpaEntity)).thenReturn(comments);

        List<PlayerPageVideo> result = videoPersistenceAdapter.getAllVideosWithFieldsByUsername(
                username, Set.of(Field.TAGS, Field.CATEGORIES, Field.COMMENTS));

        assertEquals(1, result.size());
        PlayerPageVideo playerPageVideo = result.getFirst();
        assertEquals(video, playerPageVideo.video());
        assertEquals(tags.size(), playerPageVideo.tags().size());
        assertEquals(tags.get(0), playerPageVideo.tags().get(0));
        assertEquals(tags.get(1), playerPageVideo.tags().get(1));
        assertEquals(categories.size(), playerPageVideo.categories().size());
        assertEquals(categories.get(0), playerPageVideo.categories().get(0));
        assertEquals(categories.get(1), playerPageVideo.categories().get(1));
        assertEquals(comments.size(), playerPageVideo.comments().size());
        assertEquals(comments.get(0), playerPageVideo.comments().get(0));
        assertEquals(comments.get(1), playerPageVideo.comments().get(1));
    }


    @Test
    void getAllVideosWithFieldsByUsernameReturnsEmptyListWhenNoVideos() {
        String username = "testUser";
        when(videoRepository.findAllByUserUsername(username)).thenReturn(List.of());

        List<PlayerPageVideo> result = videoPersistenceAdapter.getAllVideosWithFieldsByUsername(username, Set.of());

        assertTrue(result.isEmpty());
    }

    @Test
    void editVideoSuccessfully() {
        String videoId = "1";
        EditVideoCommand command = new EditVideoCommand(videoId, "New Title", "New Description");
        Video video = TestObjectFactory.createDummyVideo(videoId);
        video.setTitle(command.title());
        video.setDescription(command.description());
        Set<Tag> tags = Set.of(TestObjectFactory.createDummyTag("tag1"));
        Set<Category> categories = Set.of(TestObjectFactory.createDummyCategory("category1"));
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity(videoId);

        when(videoRepository.findById(videoId)).thenReturn(Optional.of(videoJpaEntity));
        when(tagMapper.toJpaEntity(any(Tag.class))).thenReturn(TestObjectFactory.createDummyTagJpaEntity("tag1"));
        when(categoryMapper.toJpaEntity(any(Category.class))).thenReturn(TestObjectFactory.createDummyCategoryJpaEntity("category1"));

        videoPersistenceAdapter.editVideo(video);

        verify(videoRepository).save(videoJpaEntity);
        assertEquals(video.getTitle(), videoJpaEntity.getTitle());
        assertEquals(video.getDescription(), videoJpaEntity.getDescription());
    }

    @Test
    void getVideoByIdWhenVideoExists() {
        String videoId = "1";
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity(videoId);
        Video video = TestObjectFactory.createDummyVideo(videoId);

        when(videoRepository.findById(videoId)).thenReturn(Optional.of(videoJpaEntity));
        when(videoMapper.toDomain(videoJpaEntity)).thenReturn(video);

        Video result = videoPersistenceAdapter.getVideoById(videoId);

        assertEquals(video, result);
}

    @Test
    void getVideoByIdWhenVideoDoesNotExist() {
        String videoId = "999";

        when(videoRepository.findById(videoId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> videoPersistenceAdapter.getVideoById(videoId));

        assertEquals("Video not found with ID: " + videoId, exception.getMessage());
    }
}