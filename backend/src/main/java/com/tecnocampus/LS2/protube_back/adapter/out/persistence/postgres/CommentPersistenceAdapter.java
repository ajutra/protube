package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.CommentJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.VideoJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper.CommentMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.repository.CommentRepository;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.repository.UserRepository;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.repository.VideoRepository;
import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import com.tecnocampus.LS2.protube_back.port.out.DeleteCommentPort;
import com.tecnocampus.LS2.protube_back.port.out.GetCommentPort;
import com.tecnocampus.LS2.protube_back.port.out.StoreCommentPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentPersistenceAdapter implements StoreCommentPort, GetCommentPort, DeleteCommentPort {
    private final CommentRepository commentRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public void storeComment(Comment comment) {
        Optional<VideoJpaEntity> videoJpaEntity = videoRepository.findById(comment.getVideoId());
        Optional<UserJpaEntity> userJpaEntity = userRepository.findById(comment.getUsername());

        // We assume that the video and the user exist, as it's checked in the service
        if (videoJpaEntity.isPresent() && userJpaEntity.isPresent()) {
            CommentJpaEntity commentJpaEntity = commentMapper.toJpaEntity(
                    comment,
                    userJpaEntity.get(),
                    videoJpaEntity.get());

            commentRepository.save(commentJpaEntity);
        }
    }

    @Override
    public void editComment(Comment comment) {
        CommentJpaEntity commentJpaEntity = commentRepository.findById(comment.getId())
                .orElseThrow(() -> new NoSuchElementException("Comment with id: " + comment.getId() + " not found"));

        commentJpaEntity.setText(comment.getText());
        commentRepository.save(commentJpaEntity);
    }

    List<Comment> getAllCommentsByVideo(VideoJpaEntity video) {
        return commentRepository.findAllByVideoOrderByCommentIdAsc(video).stream()
                .map(commentMapper::toDomain)
                .toList();
    }

    @Override
    public List<Comment> getAllCommentsByVideoId(String videoId) {
        VideoJpaEntity videoJpaEntity = videoRepository.findById(videoId)
                .orElseThrow(() -> new NoSuchElementException("Video with id: " + videoId + " not found"));

        return getAllCommentsByVideo(videoJpaEntity);
    }

    @Override
    public List<Comment> getCommentsByUsername(String username) {
        List<CommentJpaEntity> commentJpaEntities = commentRepository.findByUserUsernameOrderByCommentIdAsc(username);

        return commentJpaEntities.stream()
                .map(commentMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteComment(String commentId) {
        commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment with id: " + commentId + " not found"));

        commentRepository.deleteById(commentId);
    }

    void deleteAllCommentsByVideo(VideoJpaEntity video) {
        commentRepository.deleteAllByVideo(video);
    }
}
