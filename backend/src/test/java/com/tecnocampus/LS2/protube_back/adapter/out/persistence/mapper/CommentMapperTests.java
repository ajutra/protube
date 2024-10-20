package com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.CommentJpaEntity;
import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentMapperTests {

    private final CommentMapper commentMapper = new CommentMapper();

    @Test
    void mapCommentJpaEntityToDomain() {
        CommentJpaEntity commentJpaEntity = TestObjectFactory.createDummyCommentJpaEntity("1");

        Comment comment = commentMapper.toDomain(commentJpaEntity);

        assertEquals(commentJpaEntity.getComment_id(), comment.id());
        assertEquals(commentJpaEntity.getVideo().getVideo_id(), comment.video_id());
        assertEquals(commentJpaEntity.getUser().getUsername(), comment.username());
        assertEquals(commentJpaEntity.getText(), comment.text());
    }
}
