package com.tecnocampus.LS2.protube_back.port.in.command;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EditVideoCommandValidationTests {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validUpdateVideoCommand() {
        EditVideoCommand command = new EditVideoCommand(1280, 720, 300, "Valid Title", "Description", "username", List.of(), List.of());
        Set<ConstraintViolation<EditVideoCommand>> violations = validator.validate(command);
        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidWidth() {
        EditVideoCommand command = new EditVideoCommand(500, 720, 300, "Valid Title", "Description", "username", List.of(), List.of());
        Set<ConstraintViolation<EditVideoCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
    }

    @Test
    void invalidHeight() {
        EditVideoCommand command = new EditVideoCommand(1280, 400, 300, "Valid Title", "Description", "username", List.of(), List.of());
        Set<ConstraintViolation<EditVideoCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
    }

    @Test
    void invalidDuration() {
        EditVideoCommand command = new EditVideoCommand(1280, 720, -10, "Valid Title", "Description", "username", List.of(), List.of());
        Set<ConstraintViolation<EditVideoCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
    }

    @Test
    void invalidTitle() {
        EditVideoCommand command = new EditVideoCommand(1280, 720, 300, "", "Description", "username", List.of(), List.of());
        Set<ConstraintViolation<EditVideoCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
    }

    @Test
    void invalidUsername() {
        EditVideoCommand command = new EditVideoCommand(1280, 720, 300, "Valid Title", "Description", "", List.of(), List.of());
        Set<ConstraintViolation<EditVideoCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
    }
}