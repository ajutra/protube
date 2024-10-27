package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.port.in.command.GetTagCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetTagUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GetTagController {
    private final GetTagUseCase getTagUseCase;

    @GetMapping("/tags/{tagName}")
    public GetTagCommand getTag(@Valid @NotBlank @PathVariable String tagName) {
        return getTagUseCase.getTag(tagName);
    }
}
