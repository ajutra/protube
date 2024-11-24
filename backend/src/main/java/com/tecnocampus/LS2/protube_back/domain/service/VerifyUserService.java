package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.User;
import com.tecnocampus.LS2.protube_back.port.in.command.VerifyUserCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.VerifyUserUseCase;
import com.tecnocampus.LS2.protube_back.port.out.GetUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyUserService implements VerifyUserUseCase {
    private final GetUserPort getUserPort;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void verifyUser(VerifyUserCommand verifyUserCommand) {
        User userToVerify = User.from(verifyUserCommand);
        User userFromPersistence = getUserPort.getUserByUsername(userToVerify.username());

        if (!userFromPersistence.username().equals(userToVerify.username())
                || !passwordEncoder.matches(userToVerify.password(), userFromPersistence.password())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
    }
}
