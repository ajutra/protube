package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.User;
import com.tecnocampus.LS2.protube_back.port.out.GetUserPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class GetUserServiceTests {

    @Mock
    private GetUserPort getUserPort;

    @InjectMocks
    private GetUserService getUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserByUsernameReturnsUser() {
        User expectedUser = TestObjectFactory.createDummyUser("1");
        when(getUserPort.getUserByUsername(expectedUser.username())).thenReturn(expectedUser);

        User result = getUserService.getUserByUsername(expectedUser.username());

        assertEquals(expectedUser, result);
    }

    @Test
    void getUserByUsernameThrowsExceptionWhenUserNotFound() {
        when(getUserPort.getUserByUsername("unknown_user")).thenThrow(new NoSuchElementException("User not found"));

        assertThrows(NoSuchElementException.class, () -> getUserService.getUserByUsername("unknown_user"));
    }
}