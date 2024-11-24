package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.port.in.command.StoreUserCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.VerifyUserCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreUserUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.VerifyUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserRestController {
    private final StoreUserUseCase storeUserUseCase;
    private final VerifyUserUseCase verifyUserUseCase;

    @PostMapping("/users/register")
    public ResponseEntity<Void> storeUser(@Valid @RequestBody StoreUserCommand storeUserCommand) {
        storeUserUseCase.storeUser(storeUserCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/users/login")
    public ResponseEntity<Void> verifyUserAuthCredentials(@Valid @RequestBody VerifyUserCommand verifyUserCommand) {
        verifyUserUseCase.verifyUser(verifyUserCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
