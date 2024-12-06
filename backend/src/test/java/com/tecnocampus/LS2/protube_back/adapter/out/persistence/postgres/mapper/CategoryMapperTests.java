package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.CategoryJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper.CategoryMapper;
import com.tecnocampus.LS2.protube_back.domain.model.Category;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryMapperTests {

    private final CategoryMapper categoryMapper = new CategoryMapper();

    @Test
    void mapCategoryJpaEntityToDomain() {
        CategoryJpaEntity categoryJpaEntity = new CategoryJpaEntity("category_name1");

        Category category = categoryMapper.toDomain(categoryJpaEntity);

        assertEquals(categoryJpaEntity.getCategory_name(), category.name());
    }

    @Test
    void mapCategoryToJpaEntity() {
        Category category = new Category("category_name2");

        CategoryJpaEntity categoryJpaEntity = categoryMapper.toJpaEntity(category);

        assertEquals(category.name(), categoryJpaEntity.getCategory_name());
    }
}