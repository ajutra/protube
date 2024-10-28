package com.tecnocampus.LS2.protube_back.port.in.command;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record StoreTagCommand(
        @Valid
        @NotBlank
        String tagName
) {
        // This is a workaround to allow the deserializer to create a StoreTagCommand object
        public static StoreTagCommand from(String tagName) {
                return new StoreTagCommand(tagName);
        }
}
