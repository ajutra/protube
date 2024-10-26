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

public class StoreTagCommandValidationTests {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void whenTagNameIsValid_thenNoConstraintViolations() {
        StoreTagCommand command = new StoreTagCommand("validTagName");

        Set<ConstraintViolation<StoreTagCommand>> violations = validator.validate(command);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenTagNameIsBlank_thenConstraintViolation() {
        StoreTagCommand command = new StoreTagCommand("");

        Set<ConstraintViolation<StoreTagCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
    }

    @Test
    void whenTagNameIsNull_thenConstraintViolation() {
        StoreTagCommand command = new StoreTagCommand(null);

        Set<ConstraintViolation<StoreTagCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
    }
}