package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.User;
import com.tecnocampus.LS2.protube_back.port.in.command.VerifyUserCommand;
import com.tecnocampus.LS2.protube_back.port.out.GetUserPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class VerifyUserServiceTests {

    @Mock
    private GetUserPort getUserPort;

    @InjectMocks
    private VerifyUserService verifyUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void verifyUserWithValidCredentials() {
        VerifyUserCommand command = new VerifyUserCommand("validUsername", "validPassword");
        User userFromPersistence = new User("validUsername", new BCryptPasswordEncoder().encode("validPassword"));

        when(getUserPort.getUserByUsername(anyString())).thenReturn(userFromPersistence);

        verifyUserService.verifyUser(command);
    }

    @Test
    void verifyUserWithInvalidPassword() {
        VerifyUserCommand command = new VerifyUserCommand("validUsername", "invalidPassword");
        User userFromPersistence = new User("validUsername", new BCryptPasswordEncoder().encode("validPassword"));

        when(getUserPort.getUserByUsername(anyString())).thenReturn(userFromPersistence);

        assertThrows(IllegalArgumentException.class, () -> verifyUserService.verifyUser(command));
    }

    @Test
    void verifyUserWithNonExistentUsername() {
        VerifyUserCommand command = new VerifyUserCommand("nonExistentUsername", "anyPassword");

        when(getUserPort.getUserByUsername(anyString())).thenThrow(new IllegalArgumentException("User not found"));

        assertThrows(IllegalArgumentException.class, () -> verifyUserService.verifyUser(command));
    }
}