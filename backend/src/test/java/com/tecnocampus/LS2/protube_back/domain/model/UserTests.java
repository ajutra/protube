package com.tecnocampus.LS2.protube_back.domain.model;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreUserCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTests {

    @Test
    void from() {
        StoreUserCommand command = TestObjectFactory.createDummyStoreUserCommand("test");
        User user = User.from(command);
        assertEquals(user.username(), command.username());
    }
}
