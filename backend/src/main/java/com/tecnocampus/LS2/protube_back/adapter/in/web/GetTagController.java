package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.port.in.command.GetTagCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllTagsUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetTagUseCase;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GetTagController {
    private final GetAllTagsUseCase getAllTagsUseCase;
    private final GetTagUseCase getTagUseCase;

    @GetMapping("/tags")
    public List<GetTagCommand> getAllTags(){ return getAllTagsUseCase.getAllTags(); }

    @GetMapping("/tags/{tagName}")
    public GetTagCommand getTag(@Valid @NotBlank @PathVariable String tagName) {
        return getTagUseCase.getTag(tagName);
    }
}
