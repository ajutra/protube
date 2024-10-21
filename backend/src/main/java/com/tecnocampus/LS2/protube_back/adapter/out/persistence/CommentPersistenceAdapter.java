package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.CommentJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.VideoJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.CommentMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.repository.CommentRepository;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.repository.UserRepository;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.repository.VideoRepository;
import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import com.tecnocampus.LS2.protube_back.port.out.StoreCommentPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentPersistenceAdapter implements StoreCommentPort {
    private final CommentRepository commentRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public void storeComment(Comment comment) {
        Optional<CommentJpaEntity> commentJpaEntity = commentRepository.findById(comment.getId());

        if (commentJpaEntity.isEmpty()) {
            Optional<VideoJpaEntity> videoJpaEntity = videoRepository.findById(comment.getVideo_id());
            Optional<UserJpaEntity> userJpaEntity = userRepository.findById(comment.getUsername());

            // We assume that the video and the user exist, as it's checked in the service
            if (videoJpaEntity.isPresent() && userJpaEntity.isPresent()) {
                commentJpaEntity = Optional.of(commentMapper.toJpaEntity(comment, userJpaEntity.get(), videoJpaEntity.get()));
                commentRepository.save(commentJpaEntity.get());
            }
        }
    }
}
