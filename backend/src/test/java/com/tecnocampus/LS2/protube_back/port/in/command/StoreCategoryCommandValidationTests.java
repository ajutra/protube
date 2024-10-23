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

public class StoreCategoryCommandValidationTests {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void whenCategoryNameIsValid_thenNoConstraintViolations() {
        StoreCategoryCommand command = new StoreCategoryCommand("categoryName");

        Set<ConstraintViolation<StoreCategoryCommand>> violations = validator.validate(command);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenCategoryNameIsBlank_thenConstraintViolation() {
        StoreCategoryCommand command = new StoreCategoryCommand("");

        Set<ConstraintViolation<StoreCategoryCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenCategoryNameIsNull_thenConstraintViolation() {
        StoreCategoryCommand command = new StoreCategoryCommand(null);

        Set<ConstraintViolation<StoreCategoryCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }
}