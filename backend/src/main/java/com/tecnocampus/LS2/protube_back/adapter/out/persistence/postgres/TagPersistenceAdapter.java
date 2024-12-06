package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.TagJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper.TagMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.repository.TagRepository;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.port.out.GetTagPort;
import com.tecnocampus.LS2.protube_back.port.out.StoreTagPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

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
      
    @Override
    public Tag getTag(String tagName) {
        return tagMapper.toDomain(tagRepository.findById(tagName)
                .orElseThrow(() -> new NoSuchElementException("Tag not found")));
    }
}
