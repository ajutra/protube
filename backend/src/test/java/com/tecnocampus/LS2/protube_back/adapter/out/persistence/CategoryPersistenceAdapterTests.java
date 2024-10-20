package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.CategoryJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.CategoryMapper;
import com.tecnocampus.LS2.protube_back.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CategoryPersistenceAdapterTests {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryPersistenceAdapter categoryPersistenceAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void storeCategoryStoresNewCategory() {
        Category category = TestObjectFactory.createDummyCategory("1");
        CategoryJpaEntity categoryJpaEntity = TestObjectFactory.createDummyCategoryJpaEntity("1");

        when(categoryRepository.findById(category.name())).thenReturn(Optional.empty());
        when(categoryMapper.toJpaEntity(category)).thenReturn(categoryJpaEntity);

        categoryPersistenceAdapter.storeCategory(category);

        verify(categoryRepository, times(1)).findById(category.name());
        verify(categoryMapper, times(1)).toJpaEntity(category);
        verify(categoryRepository, times(1)).save(categoryJpaEntity);
    }

    @Test
    void storeCategoryDoesNotStoreExistingCategory() {
        Category category = TestObjectFactory.createDummyCategory("1");
        CategoryJpaEntity categoryJpaEntity = TestObjectFactory.createDummyCategoryJpaEntity("1");

        when(categoryRepository.findById(category.name())).thenReturn(Optional.of(categoryJpaEntity));

        categoryPersistenceAdapter.storeCategory(category);

        verify(categoryRepository, times(1)).findById(category.name());
        verify(categoryMapper, never()).toJpaEntity(category);
        verify(categoryRepository, never()).save(categoryJpaEntity);
    }

    @Test
    void storeAndGetCategoryStoresNewCategoryAndReturnsAsJpaEntity() {
        Category category = TestObjectFactory.createDummyCategory("1");
        CategoryJpaEntity categoryJpaEntity = TestObjectFactory.createDummyCategoryJpaEntity("1");

        when(categoryRepository.findById(category.name())).thenReturn(Optional.empty());
        when(categoryMapper.toJpaEntity(category)).thenReturn(categoryJpaEntity);

        CategoryJpaEntity result = categoryPersistenceAdapter.storeAndGetCategory(category);

        verify(categoryRepository, times(1)).findById(category.name());
        verify(categoryMapper, times(1)).toJpaEntity(category);
        verify(categoryRepository, times(1)).save(categoryJpaEntity);

        assertEquals(category.name(), result.getCategory_name());
    }

    @Test
    void storeAndGetCategoryReturnsExistingCategoryAsJpaEntity() {
        Category category = TestObjectFactory.createDummyCategory("1");
        CategoryJpaEntity categoryJpaEntity = TestObjectFactory.createDummyCategoryJpaEntity("1");

        when(categoryRepository.findById(category.name())).thenReturn(Optional.of(categoryJpaEntity));

        CategoryJpaEntity result = categoryPersistenceAdapter.storeAndGetCategory(category);

        verify(categoryRepository, times(1)).findById(category.name());
        verify(categoryMapper, never()).toJpaEntity(category);
        verify(categoryRepository, never()).save(categoryJpaEntity);

        assertEquals(category.name(), result.getCategory_name());
    }
}
