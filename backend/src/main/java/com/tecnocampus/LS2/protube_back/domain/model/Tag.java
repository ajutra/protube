package com.tecnocampus.LS2.protube_back.domain.model;

import com.tecnocampus.LS2.protube_back.port.in.command.StoreTagCommand;

public record Tag(
        String name
) {
    static public Tag from(StoreTagCommand command) {
        return new Tag(command.tagName());
    }
}
