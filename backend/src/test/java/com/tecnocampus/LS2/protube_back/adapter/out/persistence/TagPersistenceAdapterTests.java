package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.TagJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.TagMapper;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TagPersistenceAdapterTests {
    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private TagPersistenceAdapter tagPersistenceAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void storeTagStoresNewTag() {
        Tag tag = TestObjectFactory.createDummyTag("1");
        TagJpaEntity tagJpaEntity = TestObjectFactory.createDummyTagJpaEntity("1");

        when(tagRepository.findById(tag.name())).thenReturn(Optional.empty());
        when(tagMapper.toJpaEntity(tag)).thenReturn(tagJpaEntity);

        tagPersistenceAdapter.storeTag(tag);

        verify(tagRepository, times(1)).findById(tag.name());
        verify(tagMapper, times(1)).toJpaEntity(tag);
        verify(tagRepository, times(1)).save(tagJpaEntity);
    }

    @Test
    void storeTagDoesNotStoreExistingTag() {
        Tag tag = TestObjectFactory.createDummyTag("1");
        TagJpaEntity tagJpaEntity = TestObjectFactory.createDummyTagJpaEntity("1");

        when(tagRepository.findById(tag.name())).thenReturn(Optional.of(tagJpaEntity));

        tagPersistenceAdapter.storeTag(tag);

        verify(tagRepository, times(1)).findById(tag.name());
        verify(tagMapper, never()).toJpaEntity(tag);
        verify(tagRepository, never()).save(tagJpaEntity);
    }

    @Test
    void storeAndGetTagStoresNewTagAndReturnsAsJpaEntity() {
        Tag tag = TestObjectFactory.createDummyTag("1");
        TagJpaEntity tagJpaEntity = TestObjectFactory.createDummyTagJpaEntity("1");

        when(tagRepository.findById(tag.name())).thenReturn(Optional.empty());
        when(tagMapper.toJpaEntity(tag)).thenReturn(tagJpaEntity);

        TagJpaEntity result = tagPersistenceAdapter.storeAndGetTag(tag);

        verify(tagRepository, times(1)).findById(tag.name());
        verify(tagMapper, times(1)).toJpaEntity(tag);
        verify(tagRepository, times(1)).save(tagJpaEntity);

        assertEquals(tag.name(), result.getTag_name());
    }

    @Test
    void storeAndGetTagDoesNotStoreExistingTagAndReturnsAsJpaEntity() {
        Tag tag = TestObjectFactory.createDummyTag("1");
        TagJpaEntity tagJpaEntity = TestObjectFactory.createDummyTagJpaEntity("1");

        when(tagRepository.findById(tag.name())).thenReturn(Optional.of(tagJpaEntity));

        TagJpaEntity result = tagPersistenceAdapter.storeAndGetTag(tag);

        verify(tagRepository, times(1)).findById(tag.name());
        verify(tagMapper, never()).toJpaEntity(tag);
        verify(tagRepository, never()).save(tagJpaEntity);

        assertEquals(tag.name(), result.getTag_name());
    }
}
