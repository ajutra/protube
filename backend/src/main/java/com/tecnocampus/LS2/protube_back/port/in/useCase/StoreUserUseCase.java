package com.tecnocampus.LS2.protube_back.port.in.useCase;

import com.tecnocampus.LS2.protube_back.port.in.command.StoreUserCommand;

public interface StoreUserUseCase {
    void storeUser(StoreUserCommand storeUserCommand);
}
