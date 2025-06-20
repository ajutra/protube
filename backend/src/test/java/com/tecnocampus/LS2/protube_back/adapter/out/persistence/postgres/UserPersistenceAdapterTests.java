package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper.UserMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.repository.UserRepository;
import com.tecnocampus.LS2.protube_back.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserPersistenceAdapterTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserPersistenceAdapter userPersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserByUsernameWhenUserExists() {
        User user = TestObjectFactory.createDummyUser("existingUser");
        UserJpaEntity userJpaEntity = TestObjectFactory.createDummyUserJpaEntity("existingUser");

        when(userRepository.findById("existingUser")).thenReturn(Optional.of(userJpaEntity));
        when(userMapper.toDomain(userJpaEntity)).thenReturn(user);

        User result = userPersistenceAdapter.getUserByUsername("existingUser");

        assertEquals(user, result);
    }

    @Test
    void getUserByUsernameWhenUserDoesNotExist() {
        when(userRepository.findById("nonExistingUser")).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> userPersistenceAdapter.getUserByUsername("nonExistingUser"));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void storeUserWhenUserDoesNotExist() {
        User user = TestObjectFactory.createDummyUser("newUser");
        UserJpaEntity userJpaEntity = TestObjectFactory.createDummyUserJpaEntity("newUser");

        when(userRepository.existsById("Username newUser")).thenReturn(false);
        when(userMapper.toJpaEntity(user)).thenReturn(userJpaEntity);

        userPersistenceAdapter.storeUser(user);

        verify(userRepository).save(userJpaEntity);
    }

    @Test
    void storeUserWhenUserAlreadyExists() {
        User user = TestObjectFactory.createDummyUser("existingUser");

        when(userRepository.findById(user.username()))
                .thenReturn(Optional.of(TestObjectFactory.createDummyUserJpaEntity("existingUser")));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userPersistenceAdapter.storeUser(user));

        assertEquals("User already exists", exception.getMessage());
        verify(userRepository, never()).save(any(UserJpaEntity.class));
    }

    @Test
    void checkIfUserExistsWhenUserExists() {
        when(userRepository.findById("existingUser")).thenReturn(Optional.of(new UserJpaEntity()));

        assertDoesNotThrow(() -> userPersistenceAdapter.checkIfUserExists("existingUser"));
    }

    @Test
    void checkIfUserExistsWhenUserDoesNotExist() {
        when(userRepository.findById("nonExistingUser")).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> userPersistenceAdapter.checkIfUserExists("nonExistingUser"));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void findByUsernameReturnsUserJpaEntityWhenUserExists() {
        UserJpaEntity userJpaEntity = TestObjectFactory.createDummyUserJpaEntity("existingUser");

        when(userRepository.findById("existingUser")).thenReturn(Optional.of(userJpaEntity));

        UserJpaEntity result = userPersistenceAdapter.findByUsername("existingUser");

        assertEquals(userJpaEntity, result);
    }

    @Test
    void findByUsernameThrowsNoSuchElementExceptionWhenUserDoesNotExist() {
        when(userRepository.findById("nonExistingUser")).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> userPersistenceAdapter.findByUsername("nonExistingUser"));

        assertEquals("User not found", exception.getMessage());
    }
}