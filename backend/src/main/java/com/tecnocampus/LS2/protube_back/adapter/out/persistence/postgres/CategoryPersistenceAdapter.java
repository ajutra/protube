package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.CategoryJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper.CategoryMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.repository.CategoryRepository;
import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.port.out.GetCategoryPort;
import com.tecnocampus.LS2.protube_back.port.out.StoreCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements StoreCategoryPort, GetCategoryPort {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Category storeAndGetCategory(Category category) {
        CategoryJpaEntity categoryJpaEntity = categoryMapper.toJpaEntity(category);

        categoryRepository.findById(categoryJpaEntity.getCategory_name())
                .ifPresentOrElse(
                        exists -> {
                            throw new IllegalArgumentException("A category with the same name already exists");
                        },
                        () -> categoryRepository.save(categoryJpaEntity)
                );

        return categoryMapper.toDomain(categoryJpaEntity);
    }

    @Override
    public void storeCategory(Category category) {
        storeAndGetCategory(category);
    }

    @Override
    public List<Category> getAllCategories() {
        List<CategoryJpaEntity> categoriesJpaEntities = categoryRepository.findAll();
        return categoriesJpaEntities.stream()
                .map(categoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Category getCategory(String categoryName) {
        CategoryJpaEntity categoryJpaEntity = categoryRepository.findById(categoryName)
                .orElseThrow(() -> new NoSuchElementException("Category not found"));

        return categoryMapper.toDomain(categoryJpaEntity);
    }
}
