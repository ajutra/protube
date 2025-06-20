package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.TagJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper.TagMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.repository.TagRepository;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TagPersistenceAdapterTests {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private TagPersistenceAdapter tagPersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void storeAndGetTagWhenTagDoesNotExist() {
        Tag tag = TestObjectFactory.createDummyTag("newTag");
        TagJpaEntity tagJpaEntity = TestObjectFactory.createDummyTagJpaEntity("newTag");

        when(tagMapper.toJpaEntity(tag)).thenReturn(tagJpaEntity);
        when(tagRepository.findById(tagJpaEntity.getTag_name())).thenReturn(Optional.empty());
        when(tagMapper.toDomain(tagJpaEntity)).thenReturn(tag);

        Tag result = tagPersistenceAdapter.storeAndGetTag(tag);

        assertEquals(tag, result);
        verify(tagRepository).save(tagJpaEntity);
    }

    @Test
    void storeAndGetTagWhenTagAlreadyExists() {
        Tag tag = TestObjectFactory.createDummyTag("existingTag");
        TagJpaEntity tagJpaEntity = TestObjectFactory.createDummyTagJpaEntity("existingTag");

        when(tagMapper.toJpaEntity(tag)).thenReturn(tagJpaEntity);
        when(tagRepository.findById(tagJpaEntity.getTag_name())).thenReturn(Optional.of(tagJpaEntity));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> tagPersistenceAdapter.storeAndGetTag(tag));

        assertEquals("A tag with the same name already exists", exception.getMessage());
        verify(tagRepository, never()).save(any(TagJpaEntity.class));
    }

    @Test
    void storeTagWhenTagDoesNotExist() {
        Tag tag = TestObjectFactory.createDummyTag("newTag");
        TagJpaEntity tagJpaEntity = TestObjectFactory.createDummyTagJpaEntity("newTag");

        when(tagMapper.toJpaEntity(tag)).thenReturn(tagJpaEntity);
        when(tagRepository.findById(tagJpaEntity.getTag_name())).thenReturn(Optional.empty());
        tagPersistenceAdapter.storeTag(tag);

        verify(tagRepository).save(any(TagJpaEntity.class));
    }

    @Test
    void storeTagWhenTagAlreadyExists() {
        Tag tag = TestObjectFactory.createDummyTag("existingTag");
        TagJpaEntity tagJpaEntity = TestObjectFactory.createDummyTagJpaEntity("existingTag");

        when(tagMapper.toJpaEntity(tag)).thenReturn(tagJpaEntity);
        when(tagRepository.findById(tagJpaEntity.getTag_name())).thenReturn(Optional.of(tagJpaEntity));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> tagPersistenceAdapter.storeTag(tag));

        assertEquals("A tag with the same name already exists", exception.getMessage());
        verify(tagRepository, never()).save(any(TagJpaEntity.class));
    }

    @Test
    void getTagWhenTagExists() {
        Tag tag = TestObjectFactory.createDummyTag("existingTag");
        TagJpaEntity tagJpaEntity = TestObjectFactory.createDummyTagJpaEntity("existingTag");

        when(tagRepository.findById(tagJpaEntity.getTag_name())).thenReturn(Optional.of(tagJpaEntity));
        when(tagMapper.toDomain(tagJpaEntity)).thenReturn(tag);

        Tag result = tagPersistenceAdapter.getTag(tag.name());

        assertEquals(tag, result);
    }

    @Test
    void getTagWhenTagDoesNotExist() {
        Tag tag = TestObjectFactory.createDummyTag("nonExistingTag");

        when(tagRepository.findById(tag.name())).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> tagPersistenceAdapter.getTag(tag.name()));

        assertEquals("Tag not found", exception.getMessage());
    }

    @Test
    void getAllTagsReturnsListOfTags() {
        Tag tag1 = TestObjectFactory.createDummyTag("tag1");
        Tag tag2 = TestObjectFactory.createDummyTag("tag2");
        TagJpaEntity tagJpaEntity1 = TestObjectFactory.createDummyTagJpaEntity("tag1");
        TagJpaEntity tagJpaEntity2 = TestObjectFactory.createDummyTagJpaEntity("tag2");

        when(tagRepository.findAll()).thenReturn(List.of(tagJpaEntity1, tagJpaEntity2));
        when(tagMapper.toDomain(tagJpaEntity1)).thenReturn(tag1);
        when(tagMapper.toDomain(tagJpaEntity2)).thenReturn(tag2);

        assertEquals(2, tagPersistenceAdapter.getAllTags().size());
        assertEquals(tag1.name(), tagPersistenceAdapter.getAllTags().getFirst().name());
        assertEquals(tag2.name(), tagPersistenceAdapter.getAllTags().getLast().name());
        assertEquals(List.of(tag1, tag2), tagPersistenceAdapter.getAllTags());
    }

    @Test
    void getAllTagsReturnsEmptyList() {
        when(tagRepository.findAll()).thenReturn(List.of());
        assertEquals(0, tagPersistenceAdapter.getAllTags().size());
    }
}