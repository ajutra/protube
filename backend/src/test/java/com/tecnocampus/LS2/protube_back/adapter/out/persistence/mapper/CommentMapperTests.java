package com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.CommentJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.VideoJpaEntity;
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

    @Test
    void mapCommentToJpaEntity() {
        VideoJpaEntity video = TestObjectFactory.createDummyVideoJpaEntity("1");
        UserJpaEntity user = TestObjectFactory.createDummyUserJpaEntity("1");
        Comment comment = TestObjectFactory.createDummyComment("1");

        CommentJpaEntity commentJpaEntity = commentMapper.toJpaEntity(comment, user, video);

        assertEquals(comment.video_id(), commentJpaEntity.getVideo().getVideo_id());
        assertEquals(comment.username(), commentJpaEntity.getUser().getUsername());
        assertEquals(comment.text(), commentJpaEntity.getText());
    }
}
