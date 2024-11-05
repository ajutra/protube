package com.tecnocampus.LS2.protube_back.domain.model;

import java.util.List;

public record PlayerPageVideo(
        Video video,
        List<Tag> tags,
        List<Category> categories,
        List<Comment> comments
) {
    public static PlayerPageVideo from(
            Video video,
            List<Tag> tags,
            List<Category> categories,
            List<Comment> comments) {
        return new PlayerPageVideo(video, tags, categories, comments);
    }
}
