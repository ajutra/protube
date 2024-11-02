package com.tecnocampus.LS2.protube_back.domain.model;

import java.util.List;

public record VideoWithAllData(
        Video video,
        List<Tag> tags,
        List<Category> categories,
        List<Comment> comments
) {
    public static VideoWithAllData from(
            Video video,
            List<Tag> tags,
            List<Category> categories,
            List<Comment> comments) {
        return new VideoWithAllData(video, tags, categories, comments);
    }
}
