package com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.CommentJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.VideoJpaEntity;
import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentMapperTests {

    private final CommentMapper commentMapper = new CommentMapper();

    @Test
    void mapCommentJpaEntityToDomain() {
        UserJpaEntity userJpaEntity = new UserJpaEntity("user_id1", "username1");
        VideoJpaEntity videoJpaEntity = new VideoJpaEntity("video_id1", 1920, 1080, 300, "Title 1", "Description 1", userJpaEntity, new HashSet<>(), new HashSet<>());
        CommentJpaEntity commentJpaEntity = new CommentJpaEntity("comment_id1", "comment text1", userJpaEntity, videoJpaEntity);

        Comment comment = commentMapper.toDomain(commentJpaEntity);

        assertEquals("comment_id1", comment.id());
        assertEquals("video_id1", comment.video_id());
        assertEquals("user_id1", comment.user_id());
        assertEquals("comment text1", comment.text());
    }
}
