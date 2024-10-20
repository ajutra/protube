package com.tecnocampus.LS2.protube_back;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.CommentJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.VideoJpaEntity;
import com.tecnocampus.LS2.protube_back.domain.model.User;
import com.tecnocampus.LS2.protube_back.domain.model.Video;

import java.util.HashSet;

public class TestObjectFactory {
    public static UserJpaEntity createDummyUserJpaEntity(String id) {
        return new UserJpaEntity("Username " + id);
    }

    public static User createDummyUser(String id) {
        return new User("Username " + id);
    }

    public static VideoJpaEntity createDummyVideoJpaEntity(String id, UserJpaEntity userJpaEntity) {
        return new VideoJpaEntity(
                id,
                1920,
                1080,
                300,
                "Title " + id,
                "Description " + id,
                "Video File Name " + id,
                "Thumbnail File Name " + id,
                userJpaEntity,
                new HashSet<>(),
                new HashSet<>());
    }

    public static VideoJpaEntity createDummyVideoJpaEntity(String id) {
        return createDummyVideoJpaEntity(id, createDummyUserJpaEntity(id));
    }

    public static Video createDummyVideo(String id, User user) {
        return new Video(
                id,
                1920,
                1080,
                300,
                "Title " + id,
                "Description " + id,
                user.username(),
                "Video File Name " + id,
                "Thumbnail File Name " + id);
    }

    public static Video createDummyVideo(String id) {
        return createDummyVideo(id, createDummyUser(id));
    }

    public static CommentJpaEntity createDummyCommentJpaEntity(
            String id,
            UserJpaEntity userJpaEntity,
            VideoJpaEntity videoJpaEntity) {
        return new CommentJpaEntity(
                id,
                "Comment Text " + id,
                userJpaEntity,
                videoJpaEntity);
    }

    public static CommentJpaEntity createDummyCommentJpaEntity(String id) {
        return createDummyCommentJpaEntity(id, createDummyUserJpaEntity(id), createDummyVideoJpaEntity(id));
    }
}
