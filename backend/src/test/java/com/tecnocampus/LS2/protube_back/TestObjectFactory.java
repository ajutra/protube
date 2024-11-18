package com.tecnocampus.LS2.protube_back;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.*;
import com.tecnocampus.LS2.protube_back.domain.model.*;
import com.tecnocampus.LS2.protube_back.port.in.command.*;

import java.util.HashSet;
import java.util.List;

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

    public static Comment createDummyComment(String id, User user, Video video) {
        return new Comment(
                id,
                video.getId(),
                user.username(),
                "Comment Text " + id);
    }

    public static Comment createDummyComment(String id) {
        return createDummyComment(id, createDummyUser(id), createDummyVideo(id));
    }

    public static TagJpaEntity createDummyTagJpaEntity(String id) {
        return new TagJpaEntity("Tag name " + id);
    }

    public static Tag createDummyTag(String id) {
        return new Tag("Tag name " + id);
    }

    public static StoreTagCommand createDummyStoreTagCommand(String id) {
        return new StoreTagCommand("Tag name " + id);
    }

    public static CategoryJpaEntity createDummyCategoryJpaEntity(String id) {
        return new CategoryJpaEntity("Category name " + id);
    }

    public static StoreCategoryCommand createDummyStoreCategoryCommand(String id) {
        return new StoreCategoryCommand("Category name " + id);
    }

    public static Category createDummyCategory(String id) {
        return new Category("Category name " + id);
    }

    public static GetCategoryCommand createDummyGetCategoryCommand(String id) {
        return new GetCategoryCommand("Category name " + id);
    }

    public static StoreVideoCommand createDummyStoreVideoCommand(String id) {
        return new StoreVideoCommand(
                1920,
                1080,
                300,
                "Title " + id,
                "Description " + id,
                createDummyUser(id).username(),
                "Video File Name " + id,
                "Thumbnail File Name " + id,
                List.of(new StoreTagCommand("Tag name " + id)),
                List.of(new StoreCategoryCommand("Category name " + id)),
                List.of(new StoreCommentCommand("Video ID " + id, "Username " + id, "Comment Text " + id)));
    }

    public static StoreUserCommand createDummyStoreUserCommand(String id) {
        return new StoreUserCommand("Username " + id);
    }

    public static StoreCommentCommand createDummyStoreCommentCommand(String id) {
        return new StoreCommentCommand("Video ID " + id, "Username " + id, "Comment Text " + id);
    }
    public static GetCommentCommand createDummyGetCommentCommand(String id) {
        return new GetCommentCommand(
                "Video ID " + id,
                "Username " + id,
                "Comment Text " + id);
    }

    public static GetTagCommand createDummyGetTagCommand(String id) {
        return GetTagCommand.from(createDummyTag(id));
    }

    public static GetVideoCommand createDummyGetVideoCommand(String videoId) {
        Video video = createDummyVideo(videoId);
        return GetVideoCommand.from(video, List.of(), List.of(), List.of());
    }

    public static PlayerPageVideo createDummyPlayerPageVideo(String videoId) {
        Video video = createDummyVideo(videoId);
        List<Category> categories = List.of(createDummyCategory(videoId));
        List<Tag> tags = List.of(createDummyTag(videoId));
        List<Comment> comments = List.of(createDummyComment(videoId));
        return new PlayerPageVideo(video, tags, categories, comments);
    }

}

