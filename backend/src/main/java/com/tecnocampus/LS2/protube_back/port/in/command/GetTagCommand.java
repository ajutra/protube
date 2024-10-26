package com.tecnocampus.LS2.protube_back.port.in.command;

import com.tecnocampus.LS2.protube_back.domain.model.Tag;

public record GetTagCommand(
        String tagName
) {
    public static GetTagCommand from(Tag tag) {
        return new GetTagCommand(tag.name());
    }
}
