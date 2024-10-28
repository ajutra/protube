package com.tecnocampus.LS2.protube_back.port.in.command;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record StoreCommentCommand (
        @Valid
        @NotBlank
        String videoId,

        @Valid
        @NotBlank
        String username,

        @Valid
        @NotBlank
        String text
){
        // This is a workaround to allow the deserializer to create a StoreCommentCommand object
        public static StoreCommentCommand from(String username, String text) {
                return new StoreCommentCommand("dummyId", username, text);
        }
}
