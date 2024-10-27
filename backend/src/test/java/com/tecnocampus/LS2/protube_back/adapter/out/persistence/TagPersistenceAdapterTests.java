package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.TagJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.TagMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.repository.TagRepository;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> tagPersistenceAdapter.getTag(tag.name()));

        assertEquals("Tag not found", exception.getMessage());
    }
}