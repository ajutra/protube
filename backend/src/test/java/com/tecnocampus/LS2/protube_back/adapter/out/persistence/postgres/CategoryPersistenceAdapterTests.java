package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.CategoryJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper.CategoryMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.repository.CategoryRepository;
import com.tecnocampus.LS2.protube_back.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void getCategoryWhenCategoryExists() {
        Category category = TestObjectFactory.createDummyCategory("existingCategory");
        CategoryJpaEntity categoryJpaEntity = TestObjectFactory.createDummyCategoryJpaEntity("existingCategory");

        when(categoryRepository.findById(categoryJpaEntity.getCategory_name())).thenReturn(Optional.of(categoryJpaEntity));
        when(categoryMapper.toDomain(categoryJpaEntity)).thenReturn(category);

        Category result = categoryPersistenceAdapter.getCategory(category.name());

        assertEquals(category, result);
    }

    @Test
    void getCategoryWhenCategoryDoesNotExist() {
        Category category = TestObjectFactory.createDummyCategory("nonExistingCategory");

        when(categoryRepository.findById(category.name())).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> categoryPersistenceAdapter.getCategory(category.name()));

        assertEquals("Category not found", exception.getMessage());
    }

    @Test
    void getAllCategories() {
        Category category1 = TestObjectFactory.createDummyCategory("category1");
        Category category2 = TestObjectFactory.createDummyCategory("category2");
        CategoryJpaEntity categoryJpaEntity1 = TestObjectFactory.createDummyCategoryJpaEntity("category1");
        CategoryJpaEntity categoryJpaEntity2 = TestObjectFactory.createDummyCategoryJpaEntity("category2");

        when(categoryRepository.findAll()).thenReturn(List.of(categoryJpaEntity1, categoryJpaEntity2));
        when(categoryMapper.toDomain(categoryJpaEntity1)).thenReturn(category1);
        when(categoryMapper.toDomain(categoryJpaEntity2)).thenReturn(category2);

        List<Category> categories = categoryPersistenceAdapter.getAllCategories();
        List<Category> expectedCategories = List.of(category1, category2);

        assertEquals(2, categories.size());
        assertEquals(expectedCategories.getFirst().name(), categories.getFirst().name());
        assertEquals(expectedCategories.getLast().name(), categories.getLast().name());
    }

    @Test
    void getAllCategoriesWhenNoCategories() {
        when(categoryRepository.findAll()).thenReturn(List.of());
        List<Category> categories = categoryPersistenceAdapter.getAllCategories();
        assertTrue(categories.isEmpty());
    }
}