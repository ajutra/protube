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
        UserJpaEntity userJpaEntity = new UserJpaEntity("username1");
        VideoJpaEntity videoJpaEntity = new VideoJpaEntity(
                "video_id1",
                1920,
                1080,
                300,
                "Title 1",
                "Description 1",
                "Video File Name",
                "Thumbnail File Name",
                userJpaEntity,
                new HashSet<>(),
                new HashSet<>());
        CommentJpaEntity commentJpaEntity = new CommentJpaEntity(
                "comment_id1",
                "comment text1",
                userJpaEntity,
                videoJpaEntity);

        Comment comment = commentMapper.toDomain(commentJpaEntity);

        assertEquals(commentJpaEntity.getComment_id(), comment.id());
        assertEquals(commentJpaEntity.getVideo().getVideo_id(), comment.video_id());
        assertEquals(commentJpaEntity.getUser().getUsername(), comment.username());
        assertEquals(commentJpaEntity.getText(), comment.text());
    }
}
