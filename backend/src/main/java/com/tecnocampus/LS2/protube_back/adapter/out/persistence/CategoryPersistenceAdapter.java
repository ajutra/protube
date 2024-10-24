package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.CategoryJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.CategoryMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.repository.CategoryRepository;
import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.port.out.GetCategoriesPort;
import com.tecnocampus.LS2.protube_back.port.out.StoreCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements StoreCategoryPort, GetCategoriesPort {
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
}
