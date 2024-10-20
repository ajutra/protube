package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.*;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.VideoMapper;
import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class VideoPersistenceAdapterTests {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VideoMapper videoMapper;

    @Mock
    private TagPersistenceAdapter tagPersistenceAdapter;

    @Mock
    private CategoryPersistenceAdapter categoryPersistenceAdapter;

    @Mock
    private CommentPersistenceAdapter commentPersistenceAdapter;

    @InjectMocks
    private VideoPersistenceAdapter videoPersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void storeAndGetTagsReturnsSetOfTagJpaEntitiesWhenTagsExist() {
        Tag tag1 = TestObjectFactory.createDummyTag("1");
        Tag tag2 = TestObjectFactory.createDummyTag("2");
        TagJpaEntity tagJpaEntity1 = TestObjectFactory.createDummyTagJpaEntity("1");
        TagJpaEntity tagJpaEntity2 = TestObjectFactory.createDummyTagJpaEntity("2");

        when(tagRepository.findById(tag1.name())).thenReturn(Optional.of(tagJpaEntity1));
        when(tagRepository.findById(tag2.name())).thenReturn(Optional.of(tagJpaEntity2));

        Set<TagJpaEntity> result = videoPersistenceAdapter.storeAndGetTags(Set.of(tag1, tag2));

        verify(tagPersistenceAdapter, times(0)).storeAndGetTag(any(Tag.class));

        assertEquals(2, result.size());
        assertTrue(result.contains(tagJpaEntity1));
        assertTrue(result.contains(tagJpaEntity2));
    }

    @Test
    void storeAndGetTagsReturnsSetOfTagJpaEntitiesWhenTagsDoNotExist() {
        Tag tag1 = TestObjectFactory.createDummyTag("1");
        Tag tag2 = TestObjectFactory.createDummyTag("2");
        TagJpaEntity tagJpaEntity1 = TestObjectFactory.createDummyTagJpaEntity("1");
        TagJpaEntity tagJpaEntity2 = TestObjectFactory.createDummyTagJpaEntity("2");

        when(tagRepository.findById(tag1.name())).thenReturn(Optional.empty());
        when(tagRepository.findById(tag2.name())).thenReturn(Optional.empty());
        when(tagPersistenceAdapter.storeAndGetTag(tag1)).thenReturn(tagJpaEntity1);
        when(tagPersistenceAdapter.storeAndGetTag(tag2)).thenReturn(tagJpaEntity2);

        Set<TagJpaEntity> result = videoPersistenceAdapter.storeAndGetTags(Set.of(tag1, tag2));

        verify(tagPersistenceAdapter, times(2)).storeAndGetTag(any(Tag.class));

        assertEquals(2, result.size());
        assertTrue(result.contains(tagJpaEntity1));
        assertTrue(result.contains(tagJpaEntity2));
    }

    @Test
    void storeAndGetTagsReturnsEmptySetWhenNoTags() {
        Set<TagJpaEntity> result = videoPersistenceAdapter.storeAndGetTags(Set.of());

        verify(tagPersistenceAdapter, times(0)).storeAndGetTag(any(Tag.class));

        assertTrue(result.isEmpty());
    }

    @Test
    void storeAndGetCategoriesReturnsSetOfCategoryJpaEntitiesWhenCategoriesExist() {
        Category category1 = TestObjectFactory.createDummyCategory("1");
        Category category2 = TestObjectFactory.createDummyCategory("2");
        CategoryJpaEntity categoryJpaEntity1 = TestObjectFactory.createDummyCategoryJpaEntity("1");
        CategoryJpaEntity categoryJpaEntity2 = TestObjectFactory.createDummyCategoryJpaEntity("2");

        when(categoryRepository.findById(category1.name())).thenReturn(Optional.of(categoryJpaEntity1));
        when(categoryRepository.findById(category2.name())).thenReturn(Optional.of(categoryJpaEntity2));

        Set<CategoryJpaEntity> result = videoPersistenceAdapter.storeAndGetCategories(Set.of(category1, category2));

        verify(categoryPersistenceAdapter, times(0)).storeAndGetCategory(any(Category.class));

        assertEquals(2, result.size());
        assertTrue(result.contains(categoryJpaEntity1));
        assertTrue(result.contains(categoryJpaEntity2));
    }

    @Test
    void storeAndGetCategoriesReturnsSetOfCategoryJpaEntitiesWhenCategoriesDoNotExist() {
        Category category1 = TestObjectFactory.createDummyCategory("1");
        Category category2 = TestObjectFactory.createDummyCategory("2");
        CategoryJpaEntity categoryJpaEntity1 = TestObjectFactory.createDummyCategoryJpaEntity("1");
        CategoryJpaEntity categoryJpaEntity2 = TestObjectFactory.createDummyCategoryJpaEntity("2");

        when(categoryRepository.findById(category1.name())).thenReturn(Optional.empty());
        when(categoryRepository.findById(category2.name())).thenReturn(Optional.empty());
        when(categoryPersistenceAdapter.storeAndGetCategory(category1)).thenReturn(categoryJpaEntity1);
        when(categoryPersistenceAdapter.storeAndGetCategory(category2)).thenReturn(categoryJpaEntity2);

        Set<CategoryJpaEntity> result = videoPersistenceAdapter.storeAndGetCategories(Set.of(category1, category2));

        verify(categoryPersistenceAdapter, times(2)).storeAndGetCategory(any(Category.class));

        assertEquals(2, result.size());
        assertTrue(result.contains(categoryJpaEntity1));
        assertTrue(result.contains(categoryJpaEntity2));
    }

    @Test
    void storeAndGetCategoriesReturnsEmptySetWhenNoCategories() {
        Set<CategoryJpaEntity> result = videoPersistenceAdapter.storeAndGetCategories(Set.of());

        verify(categoryPersistenceAdapter, times(0)).storeAndGetCategory(any(Category.class));

        assertTrue(result.isEmpty());
    }

    @Test
    void storeCommentsStoresCommentsWhenTheyDoNotExist() {
        Comment comment1 = TestObjectFactory.createDummyComment("1");
        Comment comment2 = TestObjectFactory.createDummyComment("2");

        when(commentRepository.findById(comment1.id())).thenReturn(Optional.empty());
        when(commentRepository.findById(comment2.id())).thenReturn(Optional.empty());

        videoPersistenceAdapter.storeComments(Set.of(comment1, comment2));

        verify(commentPersistenceAdapter, times(2)).storeComment(any(Comment.class));
    }

    @Test
    void storeCommentsDoesNotStoreCommentsWhenTheyExist() {
        Comment comment1 = TestObjectFactory.createDummyComment("1");
        Comment comment2 = TestObjectFactory.createDummyComment("2");

        when(commentRepository.findById(comment1.id())).thenReturn(Optional.of(new CommentJpaEntity()));
        when(commentRepository.findById(comment2.id())).thenReturn(Optional.of(new CommentJpaEntity()));

        videoPersistenceAdapter.storeComments(Set.of(comment1, comment2));

        verify(commentPersistenceAdapter, times(0)).storeComment(any(Comment.class));
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

        assertEquals(videoExpected1.getId(), result.getFirst().getId());
        assertEquals(videoExpected1.getWidth(), result.getFirst().getWidth());
        assertEquals(videoExpected1.getHeight(), result.getFirst().getHeight());
        assertEquals(videoExpected1.getDuration(), result.getFirst().getDuration());
        assertEquals(videoExpected1.getTitle(), result.getFirst().getTitle());
        assertEquals(videoExpected1.getDescription(), result.getFirst().getDescription());
        assertEquals(videoExpected1.getUsername(), result.getFirst().getUsername());
        assertEquals(videoExpected1.getVideoFileName(), result.getFirst().getVideoFileName());
        assertEquals(videoExpected1.getThumbnailFileName(), result.getFirst().getThumbnailFileName());

        assertEquals(videoExpected2.getId(), result.getLast().getId());
        assertEquals(videoExpected2.getWidth(), result.getLast().getWidth());
        assertEquals(videoExpected2.getHeight(), result.getLast().getHeight());
        assertEquals(videoExpected2.getDuration(), result.getLast().getDuration());
        assertEquals(videoExpected2.getTitle(), result.getLast().getTitle());
        assertEquals(videoExpected2.getDescription(), result.getLast().getDescription());
        assertEquals(videoExpected2.getUsername(), result.getLast().getUsername());
        assertEquals(videoExpected2.getVideoFileName(), result.getLast().getVideoFileName());
        assertEquals(videoExpected2.getThumbnailFileName(), result.getLast().getThumbnailFileName());
    }

    @Test
    void getAllVideosReturnsEmptyListWhenNoVideos() {
        when(videoRepository.findAll()).thenReturn(List.of());

        List<Video> result = videoPersistenceAdapter.getAllVideos();

        assertTrue(result.isEmpty());
    }

    @Test
    void storeVideoStoresVideo() {
        Video video = TestObjectFactory.createDummyVideo("1");
        Tag tag = TestObjectFactory.createDummyTag("1");
        Category category = TestObjectFactory.createDummyCategory("1");
        Comment comment = TestObjectFactory.createDummyComment("1");

        Set<Tag> tags = Set.of(tag);
        Set<Category> categories = Set.of(category);
        Set<Comment> comments = Set.of(comment);

        UserJpaEntity userJpaEntity = TestObjectFactory.createDummyUserJpaEntity("1");
        TagJpaEntity tagJpaEntity = TestObjectFactory.createDummyTagJpaEntity("1");
        CategoryJpaEntity categoryJpaEntity = TestObjectFactory.createDummyCategoryJpaEntity("1");
        VideoJpaEntity videoJpaEntity = TestObjectFactory.createDummyVideoJpaEntity("1");

        when(userRepository.findById(video.getUsername())).thenReturn(Optional.of(userJpaEntity));
        when(tagRepository.findById(tag.name())).thenReturn(Optional.of(tagJpaEntity));
        when(categoryRepository.findById(category.name())).thenReturn(Optional.of(categoryJpaEntity));
        when(videoMapper.toJpaEntity(video, userJpaEntity, Set.of(tagJpaEntity), Set.of(categoryJpaEntity))).thenReturn(videoJpaEntity);

        videoPersistenceAdapter.storeVideo(video, tags, categories, comments);

        verify(videoRepository, times(1)).save(videoJpaEntity);
        verify(tagRepository, times(1)).findById(tag.name());
        verify(categoryRepository, times(1)).findById(category.name());
        verify(commentRepository, times(1)).findById(comment.id());
    }
}