package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.CategoryJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.CategoryMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.repository.CategoryRepository;
import com.tecnocampus.LS2.protube_back.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CategoryPersistenceAdapterTests {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryPersistenceAdapter categoryPersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void storeAndGetCategoryWhenCategoryDoesNotExist() {
        Category category = TestObjectFactory.createDummyCategory("newCategory");
        CategoryJpaEntity categoryJpaEntity = TestObjectFactory.createDummyCategoryJpaEntity("newCategory");

        when(categoryMapper.toJpaEntity(category)).thenReturn(categoryJpaEntity);
        when(categoryRepository.findById(categoryJpaEntity.getCategory_name())).thenReturn(Optional.empty());
        when(categoryMapper.toDomain(categoryJpaEntity)).thenReturn(category);

        Category result = categoryPersistenceAdapter.storeAndGetCategory(category);

        assertEquals(category, result);
        verify(categoryRepository).save(categoryJpaEntity);
    }

    @Test
    void storeAndGetCategoryWhenCategoryAlreadyExists() {
        Category category = TestObjectFactory.createDummyCategory("existingCategory");
        CategoryJpaEntity categoryJpaEntity = TestObjectFactory.createDummyCategoryJpaEntity("existingCategory");

        when(categoryMapper.toJpaEntity(category)).thenReturn(categoryJpaEntity);
        when(categoryRepository.findById(categoryJpaEntity.getCategory_name())).thenReturn(Optional.of(categoryJpaEntity));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> categoryPersistenceAdapter.storeAndGetCategory(category));

        assertEquals("A category with the same name already exists", exception.getMessage());
        verify(categoryRepository, never()).save(any(CategoryJpaEntity.class));
    }

    @Test
    void storeCategoryWhenCategoryDoesNotExist() {
        Category category = TestObjectFactory.createDummyCategory("newCategory");
        CategoryJpaEntity categoryJpaEntity = TestObjectFactory.createDummyCategoryJpaEntity("newCategory");

        when(categoryMapper.toJpaEntity(category)).thenReturn(categoryJpaEntity);
        when(categoryRepository.findById(categoryJpaEntity.getCategory_name())).thenReturn(Optional.empty());

        categoryPersistenceAdapter.storeCategory(category);

        verify(categoryRepository).save(any(CategoryJpaEntity.class));
    }

    @Test
    void storeCategoryWhenCategoryAlreadyExists() {
        Category category = TestObjectFactory.createDummyCategory("existingCategory");
        CategoryJpaEntity categoryJpaEntity = TestObjectFactory.createDummyCategoryJpaEntity("existingCategory");

        when(categoryMapper.toJpaEntity(category)).thenReturn(categoryJpaEntity);
        when(categoryRepository.findById(categoryJpaEntity.getCategory_name())).thenReturn(Optional.of(categoryJpaEntity));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> categoryPersistenceAdapter.storeCategory(category));

        assertEquals("A category with the same name already exists", exception.getMessage());
        verify(categoryRepository, never()).save(any(CategoryJpaEntity.class));
    }
}