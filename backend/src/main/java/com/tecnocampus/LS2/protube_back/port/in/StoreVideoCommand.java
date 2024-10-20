package com.tecnocampus.LS2.protube_back.port.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record StoreVideoCommand(
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

        String videoFileName,
        String thumbnailFileName,
        List<@NotBlank String> tags,
        List<@NotBlank String> categories
) {
}
