package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.port.in.command.StoreVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreVideoUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreVideoController {
    private final StoreVideoUseCase storeVideoUseCase;

    @PostMapping("/videos")
    public ResponseEntity<Void> storeVideo(@Valid @RequestBody StoreVideoCommand storeVideoCommand) {
        storeVideoUseCase.storeVideo(storeVideoCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
