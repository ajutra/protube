package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.port.in.command.GetTagCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreTagCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllTagsUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetTagUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreTagUseCase;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TagRestController {
    private final GetAllTagsUseCase getAllTagsUseCase;
    private final GetTagUseCase getTagUseCase;
    private final StoreTagUseCase storeTagUseCase;

    @GetMapping("/tags")
    public List<GetTagCommand> getAllTags(){ return getAllTagsUseCase.getAllTags(); }

    @GetMapping("/tags/{tagName}")
    public GetTagCommand getTag(@Valid @NotBlank @PathVariable String tagName) {
        return getTagUseCase.getTag(tagName);
    }

    @PostMapping("/tags")
    public ResponseEntity<Void> storeTag(@Valid @RequestBody StoreTagCommand storeTagCommand) {
        storeTagUseCase.storeTag(storeTagCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
