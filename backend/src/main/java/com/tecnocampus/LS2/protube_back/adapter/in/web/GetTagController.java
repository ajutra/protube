package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.port.in.command.GetTagCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllTagsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GetTagController {
    private final GetAllTagsUseCase getAllTagsUseCase;

    @GetMapping("/tags")
    public List<GetTagCommand> getAllTags(){ return getAllTagsUseCase.getAllTags(); }
}
