package com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.TagJpaEntity;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagMapperTests {

    private final TagMapper tagMapper = new TagMapper();

    @Test
    void mapTagJpaEntityToDomain() {
        TagJpaEntity tagJpaEntity = new TagJpaEntity("tag_name1");

        Tag tag = tagMapper.toDomain(tagJpaEntity);

        assertEquals(tagJpaEntity.getTag_name(), tag.name());
    }
}