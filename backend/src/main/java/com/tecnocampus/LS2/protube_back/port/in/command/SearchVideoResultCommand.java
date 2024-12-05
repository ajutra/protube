package com.tecnocampus.LS2.protube_back.port.in.command;

import com.tecnocampus.LS2.protube_back.domain.model.Video;

public record SearchVideoResultCommand(
        String id,
        String title
) {
    public static SearchVideoResultCommand from(Video video) {
        return new SearchVideoResultCommand(video.getId(), video.getTitle());
    }
}
