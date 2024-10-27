package com.tecnocampus.LS2.protube_back.port.in.command;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StoreUserCommandValidationTests {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void whenUsernameIsValid_thenNoConstraintViolations() {
        StoreUserCommand command = new StoreUserCommand("validUsername");

        Set<ConstraintViolation<StoreUserCommand>> violations = validator.validate(command);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenUsernameIsBlank_thenConstraintViolation() {
        StoreUserCommand command = new StoreUserCommand("");

        Set<ConstraintViolation<StoreUserCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
    }

    @Test
    void whenUsernameIsNull_thenConstraintViolation() {
        StoreUserCommand command = new StoreUserCommand(null);

        Set<ConstraintViolation<StoreUserCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
    }
}