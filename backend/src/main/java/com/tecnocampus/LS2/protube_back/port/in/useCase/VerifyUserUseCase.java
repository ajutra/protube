package com.tecnocampus.LS2.protube_back.port.in.useCase;

import com.tecnocampus.LS2.protube_back.port.in.command.VerifyUserCommand;

public interface VerifyUserUseCase {
    void verifyUser(VerifyUserCommand verifyUserCommand);
}
