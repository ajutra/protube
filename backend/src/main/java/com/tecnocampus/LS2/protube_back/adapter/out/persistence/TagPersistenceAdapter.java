package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.TagJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.TagMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.repository.TagRepository;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.port.out.GetTagPort;
import com.tecnocampus.LS2.protube_back.port.out.StoreTagPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TagPersistenceAdapter implements StoreTagPort, GetTagPort {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public Tag storeAndGetTag(Tag tag) {
        TagJpaEntity tagJpaEntity = tagMapper.toJpaEntity(tag);

        tagRepository.findById(tagJpaEntity.getTag_name())
                .ifPresentOrElse(
                        exists -> {
                            throw new IllegalArgumentException("A tag with the same name already exists");
                        },
                        () -> tagRepository.save(tagJpaEntity)
                );

        return tagMapper.toDomain(tagJpaEntity);
    }

    @Override
    public void storeTag(Tag tag) {
        storeAndGetTag(tag);
    }


    @Override
    public List<Tag> getAllTags() {
        List<TagJpaEntity> tagJpaEntities = tagRepository.findAll();
        return tagJpaEntities.stream()
                .map(tagMapper::toDomain)
                .toList();
    }
}
