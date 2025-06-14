package com.tecnocampus.LS2.protube_back.port.in.command;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record StoreUserCommand(
        @Valid
        @NotBlank
        String username,

        @Valid
        @NotBlank
        String password
) {
        public static StoreUserCommand from(String username) {
                return new StoreUserCommand(username, "");
        }

        public static StoreUserCommand from(String username, String password) {
                return new StoreUserCommand(username, password);
        }
}
