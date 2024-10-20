package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.*;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.VideoMapper;
import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.out.GetVideosPort;
import com.tecnocampus.LS2.protube_back.port.out.StoreVideoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VideoPersistenceAdapter implements GetVideosPort, StoreVideoPort {
    private final VideoRepository videoRepository;
    private final CommentRepository commentRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final VideoMapper videoMapper;
    private final TagPersistenceAdapter tagPersistenceAdapter;
    private final CategoryPersistenceAdapter categoryPersistenceAdapter;
    private final CommentPersistenceAdapter commentPersistenceAdapter;

    @Override
    public List<Video> getAllVideos() {
        return videoRepository.findAll().stream()
                .map(videoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void storeVideo(Video video, Set<Tag> tags, Set<Category> categories, Set<Comment> comments) {
        Optional<UserJpaEntity> userJpaEntity = userRepository.findById(video.getUsername());
        Set<TagJpaEntity> tagsJpa = storeAndGetTags(tags);
        Set<CategoryJpaEntity> categoriesJpa = storeAndGetCategories(categories);

        // We assume that the video doesn't exist and the user exists, as it's checked in the service
        if (userJpaEntity.isPresent()) {
            VideoJpaEntity videoJpaEntity = videoMapper.toJpaEntity(video, userJpaEntity.get(), tagsJpa, categoriesJpa);
            videoRepository.save(videoJpaEntity);
            storeComments(comments);
        }
    }

    Set<TagJpaEntity> storeAndGetTags(Set<Tag> tags) {
        return tags.stream()
                .map(tag -> tagRepository.findById(tag.name())
                        .orElseGet(() -> tagPersistenceAdapter.storeAndGetTag(tag)))
                .collect(Collectors.toSet());
    }

    Set<CategoryJpaEntity> storeAndGetCategories(Set<Category> categories) {
        return categories.stream()
                .map(category -> categoryRepository.findById(category.name())
                        .orElseGet(() -> categoryPersistenceAdapter.storeAndGetCategory(category)))
                .collect(Collectors.toSet());
    }

    void storeComments(Set<Comment> comments) {
        comments.forEach(comment -> {
            if (commentRepository.findById(comment.id()).isEmpty()) {
                commentPersistenceAdapter.storeComment(comment);
            }
        });
    }
}
