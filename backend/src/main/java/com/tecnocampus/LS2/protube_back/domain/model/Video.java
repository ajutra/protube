package com.tecnocampus.LS2.protube_back.domain.model;

public record Video(
        String id,
        int width,
        int height,
        int duration,
        String title,
        String description,
        String username
) {
}
