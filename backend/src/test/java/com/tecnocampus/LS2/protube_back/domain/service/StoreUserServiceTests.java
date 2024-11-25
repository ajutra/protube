package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.User;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreUserCommand;
import com.tecnocampus.LS2.protube_back.port.out.StoreUserPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StoreUserServiceTests {

    @Mock
    private StoreUserPort storeUserPort;

    @InjectMocks
    private StoreUserService storeUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void storeUserSuccessfully() {
        StoreUserCommand command = TestObjectFactory.createDummyStoreUserCommand("1");

        storeUserService.storeUser(command);

        verify(storeUserPort, times(1)).storeUser(any(User.class));
    }
}