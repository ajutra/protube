package com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.CommentJpaEntity;
import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public Comment toDomain(CommentJpaEntity commentJpaEntity) {
        return new Comment(
                commentJpaEntity.getComment_id(),
                commentJpaEntity.getVideo().getVideo_id(),
                commentJpaEntity.getUser().getUser_id(),
                commentJpaEntity.getText()
        );
    }
}
