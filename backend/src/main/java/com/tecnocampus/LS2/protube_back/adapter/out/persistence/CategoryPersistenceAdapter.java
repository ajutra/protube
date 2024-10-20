package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.CategoryJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.CategoryMapper;
import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.port.out.StoreCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements StoreCategoryPort {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public void storeCategory(Category category) {
        storeAndGetCategory(category);
    }

    CategoryJpaEntity storeAndGetCategory(Category category) {
        Optional<CategoryJpaEntity> categoryJpaEntity = categoryRepository.findById(category.name());

        if (categoryJpaEntity.isEmpty()) {
            categoryJpaEntity = Optional.of(categoryMapper.toJpaEntity(category));
            categoryRepository.save(categoryJpaEntity.get());
        }

        return categoryJpaEntity.get();
    }
}
