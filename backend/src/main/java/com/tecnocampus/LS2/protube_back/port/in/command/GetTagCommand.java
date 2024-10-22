package com.tecnocampus.LS2.protube_back.port.in.command;

import com.tecnocampus.LS2.protube_back.domain.model.Tag;

import java.util.List;

public record GetTagCommand(
        String tagName
) {
    public static List<GetTagCommand> from(List<Tag> tags) {
        return tags.stream()
                .map(tag -> new GetTagCommand(tag.name()))
                .toList();
    }
}
