package com.tecnocampus.LS2.protube_back.domain.model;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreUserCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.VerifyUserCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTests {
    @Test
    void fromStoreUserCommand() {
        StoreUserCommand command = TestObjectFactory.createDummyStoreUserCommand("test");
        User user = User.from(command);
        assertEquals(user.username(), command.username());
        assertEquals(user.password(), command.password());
    }

    @Test
    void fromVerifyUserCommand() {
        VerifyUserCommand command = TestObjectFactory.createDummyVerifyUserCommand("test");
        User user = User.from(command);
        assertEquals(user.username(), command.username());
        assertEquals(user.password(), command.password());
    }
}
