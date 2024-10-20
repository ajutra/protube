package com.tecnocampus.LS2.protube_back.port.in;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StoreVideoCommandValidationTests {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void whenAllFieldsAreValid_thenNoConstraintViolations() {
        StoreVideoCommand command = new StoreVideoCommand(
                1920, 1080, 30, "title", "description", "username",
                "videoFileName", "thumbnailFileName", List.of("tag"), List.of("category")
        );

        Set<ConstraintViolation<StoreVideoCommand>> violations = validator.validate(command);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenWidthIsInvalid_thenConstraintViolation() {
        StoreVideoCommand command = new StoreVideoCommand(
                8000, 1080, 30, "title", "description", "username",
                "videoFileName", "thumbnailFileName", List.of("tag"), List.of("category")
        );

        Set<ConstraintViolation<StoreVideoCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
        assertEquals("must be less than or equal to 7680", violations.iterator().next().getMessage());
    }

    @Test
    void whenHeightIsInvalid_thenConstraintViolation() {
        StoreVideoCommand command = new StoreVideoCommand(
                1920, 5000, 30, "title", "description", "username",
                "videoFileName", "thumbnailFileName", List.of("tag"), List.of("category")
        );

        Set<ConstraintViolation<StoreVideoCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
        assertEquals("must be less than or equal to 4320", violations.iterator().next().getMessage());
    }

    @Test
    void whenDurationIsInvalid_thenConstraintViolation() {
        StoreVideoCommand command = new StoreVideoCommand(
                1920, 1080, -10, "title", "description", "username",
                "videoFileName", "thumbnailFileName", List.of("tag"), List.of("category")
        );

        Set<ConstraintViolation<StoreVideoCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
        assertEquals("must be greater than 0", violations.iterator().next().getMessage());
    }

    @Test
    void whenTitleIsBlank_thenConstraintViolation() {
        StoreVideoCommand command = new StoreVideoCommand(
                1920, 1080, 30, "", "description", "username",
                "videoFileName", "thumbnailFileName", List.of("tag"), List.of("category")
        );

        Set<ConstraintViolation<StoreVideoCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenUsernameIsBlank_thenConstraintViolation() {
        StoreVideoCommand command = new StoreVideoCommand(
                1920, 1080, 30, "title", "description", "",
                "videoFileName", "thumbnailFileName", List.of("tag"), List.of("category")
        );

        Set<ConstraintViolation<StoreVideoCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenTagsContainBlank_thenConstraintViolation() {
        StoreVideoCommand command = new StoreVideoCommand(
                1920, 1080, 30, "title", "description", "username",
                "videoFileName", "thumbnailFileName", List.of(""), List.of("category")
        );

        Set<ConstraintViolation<StoreVideoCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenCategoriesContainBlank_thenConstraintViolation() {
        StoreVideoCommand command = new StoreVideoCommand(
                1920, 1080, 30, "title", "description", "username",
                "videoFileName", "thumbnailFileName", List.of("tag"), List.of("")
        );

        Set<ConstraintViolation<StoreVideoCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void whenTagsAndCategoriesAreEmpty_thenNoConstraintViolations() {
        StoreVideoCommand command = new StoreVideoCommand(
                1920, 1080, 30, "title", "description", "username",
                "videoFileName", "thumbnailFileName", List.of(), List.of());

        Set<ConstraintViolation<StoreVideoCommand>> violations = validator.validate(command);
        assertTrue(violations.isEmpty());
    }
}