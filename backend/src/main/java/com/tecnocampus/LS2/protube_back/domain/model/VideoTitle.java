package com.tecnocampus.LS2.protube_back.domain.model;

public record VideoTitle(
        String title
) {
    public static VideoTitle fromVideo(Video video) {
        return new VideoTitle(video.getTitle());
    }
}
