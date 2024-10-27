package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.*;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.CategoryMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.TagMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.VideoMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.repository.VideoRepository;
import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.out.GetVideoPort;
import com.tecnocampus.LS2.protube_back.port.out.StoreVideoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VideoPersistenceAdapter implements GetVideoPort, StoreVideoPort {
    private final VideoRepository videoRepository;
    private final UserPersistenceAdapter userPersistenceAdapter;
    private final VideoMapper videoMapper;
    private final TagMapper tagMapper;
    private final CategoryMapper categoryMapper;

    @Override
    public List<Video> getAllVideos() {
        return videoRepository.findAll().stream()
                .map(videoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Video getVideoByTitleAndUsername(String title, String username) {
        return videoRepository.findByTitleAndUserUsername(title, username)
                .map(videoMapper::toDomain)
                .orElseThrow(() -> new NoSuchElementException("Video not found"));
    }

    @Override
    public void storeVideo(Video video, Set<Tag> tags, Set<Category> categories) {
        storeAndGetVideo(video, tags, categories);
    }

    @Override
    public Video storeAndGetVideo(Video video, Set<Tag> tags, Set<Category> categories) {
        Optional<UserJpaEntity> userJpaEntity = userPersistenceAdapter.findByUsername(video.getUsername());
        Set<TagJpaEntity> tagsJpa = tags.stream().map(tagMapper::toJpaEntity).collect(Collectors.toSet());
        Set<CategoryJpaEntity> categoriesJpa = categories.stream().map(categoryMapper::toJpaEntity).collect(Collectors.toSet());

        // We assume that the video doesn't exist and the user exists, as it's checked in the service
        if (userJpaEntity.isPresent()) {
            VideoJpaEntity videoJpaEntity = videoMapper.toJpaEntity(video, userJpaEntity.get(), tagsJpa, categoriesJpa);
            videoRepository.save(videoJpaEntity);

            return videoMapper.toDomain(videoJpaEntity);
        }

        return null; // Never reached
    }
}
