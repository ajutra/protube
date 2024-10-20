package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.TagJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.TagMapper;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.port.out.StoreTagPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TagPersistenceAdapter implements StoreTagPort {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public void storeTag(Tag tag) {
        storeAndGetTag(tag);
    }

    TagJpaEntity storeAndGetTag(Tag tag) {
        Optional<TagJpaEntity> tagJpaEntity = tagRepository.findById(tag.name());

        if (tagJpaEntity.isEmpty()) {
            tagJpaEntity = Optional.of(tagMapper.toJpaEntity(tag));
            tagRepository.save(tagJpaEntity.get());
        }

        return tagJpaEntity.get();
    }
}
