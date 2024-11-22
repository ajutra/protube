package com.tecnocampus.LS2.protube_back.domain.model;

import com.tecnocampus.LS2.protube_back.port.in.command.StoreUserCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.VerifyUserCommand;

public record User(
        String username,
        String password
) {
    public static User from(StoreUserCommand command) {
        return new User(command.username(), command.password());
    }

    public static User from(VerifyUserCommand command) {
        return new User(command.username(), command.password());
    }
}
