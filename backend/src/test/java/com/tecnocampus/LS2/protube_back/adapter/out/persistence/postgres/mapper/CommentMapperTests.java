package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.CommentJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.VideoJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper.CommentMapper;
import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentMapperTests {

    private final CommentMapper commentMapper = new CommentMapper();

    @Test
    void mapCommentJpaEntityToDomain() {
        CommentJpaEntity commentJpaEntity = TestObjectFactory.createDummyCommentJpaEntity("1");

        Comment comment = commentMapper.toDomain(commentJpaEntity);

        assertEquals(commentJpaEntity.getCommentId(), comment.getId());
        assertEquals(commentJpaEntity.getVideo().getVideoId(), comment.getVideoId());
        assertEquals(commentJpaEntity.getUser().getUsername(), comment.getUsername());
        assertEquals(commentJpaEntity.getText(), comment.getText());
    }

    @Test
    void mapCommentToJpaEntity() {
        VideoJpaEntity video = TestObjectFactory.createDummyVideoJpaEntity("1");
        UserJpaEntity user = TestObjectFactory.createDummyUserJpaEntity("1");
        Comment comment = TestObjectFactory.createDummyComment("1");

        CommentJpaEntity commentJpaEntity = commentMapper.toJpaEntity(comment, user, video);

        assertEquals(comment.getVideoId(), commentJpaEntity.getVideo().getVideoId());
        assertEquals(comment.getUsername(), commentJpaEntity.getUser().getUsername());
        assertEquals(comment.getText(), commentJpaEntity.getText());
    }
}
