package com.tecnocampus.LS2.protube_back.domain.model;

import com.tecnocampus.LS2.protube_back.port.in.command.StoreUserCommand;

public record User(
        String username
) {
    public static User from(StoreUserCommand command) {
        return new User(command.username());
    }
}
