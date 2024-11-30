package com.tecnocampus.LS2.protube_back.port.in.command;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record EditVideoCommand(
        @Valid
        @NotBlank
        String id,

        @Valid
        @NotBlank
        String title,

        String description
) {
}
