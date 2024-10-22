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
}
