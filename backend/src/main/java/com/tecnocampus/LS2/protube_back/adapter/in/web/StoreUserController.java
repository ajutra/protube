package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.port.in.command.StoreUserCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreUserUseCase;
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
public class StoreUserController {
    private final StoreUserUseCase storeUserUseCase;

    @PostMapping("/users")
    public ResponseEntity<Void> storeUser(@Valid @RequestBody StoreUserCommand storeUserCommand) {
        storeUserUseCase.storeUser(storeUserCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
