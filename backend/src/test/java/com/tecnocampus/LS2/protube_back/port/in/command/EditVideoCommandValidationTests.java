package com.tecnocampus.LS2.protube_back.port.in.command;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void validEditVideoCommand() {
        EditVideoCommand command = new EditVideoCommand("1", "Valid Title", "Description");
        Set<ConstraintViolation<EditVideoCommand>> violations = validator.validate(command);
        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidId() {
        EditVideoCommand command = new EditVideoCommand("", "Valid Title", "Description");
        Set<ConstraintViolation<EditVideoCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
    }

    @Test
    void invalidTitle() {
        EditVideoCommand command = new EditVideoCommand("1", "", "Description");
        Set<ConstraintViolation<EditVideoCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
    }
}