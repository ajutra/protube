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

public class StoreCommentCommandValidationTests {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void whenAllFieldsAreValid_thenNoConstraintViolations() {
        StoreCommentCommand command = new StoreCommentCommand("videoId", "username", "text");

        Set<ConstraintViolation<StoreCommentCommand>> violations = validator.validate(command);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenVideoIdIsBlank_thenConstraintViolation() {
        StoreCommentCommand command = new StoreCommentCommand("", "username", "text");

        Set<ConstraintViolation<StoreCommentCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenUsernameIsBlank_thenConstraintViolation() {
        StoreCommentCommand command = new StoreCommentCommand("videoId", "", "text");

        Set<ConstraintViolation<StoreCommentCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenTextIsBlank_thenConstraintViolation() {
        StoreCommentCommand command = new StoreCommentCommand("videoId", "username", "");

        Set<ConstraintViolation<StoreCommentCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenVideoIdIsNull_thenConstraintViolation() {
        StoreCommentCommand command = new StoreCommentCommand(null, "username", "text");

        Set<ConstraintViolation<StoreCommentCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenUsernameIsNull_thenConstraintViolation() {
        StoreCommentCommand command = new StoreCommentCommand("videoId", null, "text");

        Set<ConstraintViolation<StoreCommentCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenTextIsNull_thenConstraintViolation() {
        StoreCommentCommand command = new StoreCommentCommand("videoId", "username", null);

        Set<ConstraintViolation<StoreCommentCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }
}