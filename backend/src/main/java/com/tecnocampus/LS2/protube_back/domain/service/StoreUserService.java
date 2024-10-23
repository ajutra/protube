package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.User;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreUserCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreUserUseCase;
import com.tecnocampus.LS2.protube_back.port.out.StoreUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreUserService implements StoreUserUseCase {
    private final StoreUserPort storeUserPort;

    public void storeUser(StoreUserCommand storeUserCommand) {
        storeUserPort.storeUser(User.from(storeUserCommand));
    }
}
