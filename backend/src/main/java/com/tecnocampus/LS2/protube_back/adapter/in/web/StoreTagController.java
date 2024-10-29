package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.port.in.command.StoreTagCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreTagUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StoreTagController {
    private final StoreTagUseCase storeTagUseCase;

    @PostMapping("/tags")
    public ResponseEntity<Void> storeTag(@Valid @RequestBody StoreTagCommand storeTagCommand) {
        storeTagUseCase.storeTag(storeTagCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
