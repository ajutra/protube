package com.tecnocampus.LS2.protube_back.port.in.command;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record UpdateVideoCommand(
        @Valid
        @NotNull
        @Min(640)
        @Max(7680)
        int width,

        @Valid
        @NotNull
        @Min(480)
        @Max(4320)
        int height,

        @Valid
        @NotNull
        @Positive
        int duration,

        @Valid
        @NotBlank
        String title,

        String description,

        @Valid
        @NotBlank
        String username,

        List<StoreTagCommand> tags,
        List<StoreCategoryCommand> categories
) {
}
