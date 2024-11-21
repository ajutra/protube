package com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.CommentJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.VideoJpaEntity;
import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public Comment toDomain(CommentJpaEntity commentJpaEntity) {
        return new Comment(
                commentJpaEntity.getCommentId(),
                commentJpaEntity.getVideo().getVideo_id(),
                commentJpaEntity.getUser().getUsername(),
                commentJpaEntity.getText()
        );
    }

    public CommentJpaEntity toJpaEntity(Comment comment, UserJpaEntity userJpaEntity, VideoJpaEntity videoJpaEntity) {
        CommentJpaEntity commentJpaEntity = new CommentJpaEntity();
        commentJpaEntity.setText(comment.getText());
        commentJpaEntity.setUser(userJpaEntity);
        commentJpaEntity.setVideo(videoJpaEntity);
        return commentJpaEntity;
    }
}
