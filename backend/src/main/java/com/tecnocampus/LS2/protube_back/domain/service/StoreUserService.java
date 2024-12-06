package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.User;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreUserCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreUserUseCase;
import com.tecnocampus.LS2.protube_back.port.out.StoreUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreUserService implements StoreUserUseCase {
    private final StoreUserPort storeUserPort;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void storeUser(StoreUserCommand storeUserCommand) {
        User user;

        // This only will happen when loading initial data, in which case the password is empty
        // In this case, we will store the user without encoding the password to save time when starting the application
        if (storeUserCommand.password().isEmpty())
            user = User.from(storeUserCommand);

        else
            user = User.from(
                StoreUserCommand.from(
                        storeUserCommand.username(),
                        passwordEncoder.encode(storeUserCommand.password())));

        storeUserPort.storeUser(user);
    }
}
