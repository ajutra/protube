package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.TagJpaEntity;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    public Tag toDomain(TagJpaEntity tagJpaEntity) {
        return new Tag(
                tagJpaEntity.getTag_name()
        );
    }

    public TagJpaEntity toJpaEntity(Tag tag) {
        return new TagJpaEntity(tag.name());
    }
}
