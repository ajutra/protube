package com.tecnocampus.LS2.protube_back.port.in.command;

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
                "videoFileName", "thumbnailFileName",
                List.of(new StoreTagCommand("tag")),
                List.of(new StoreCategoryCommand("category")),
                List.of(new StoreCommentCommand("video_id", "username", "text"))
        );

        Set<ConstraintViolation<StoreVideoCommand>> violations = validator.validate(command);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenWidthIsInvalid_thenConstraintViolation() {
        StoreVideoCommand command = new StoreVideoCommand(
                8000, 1080, 30, "title", "description", "username",
                "videoFileName", "thumbnailFileName",
                List.of(new StoreTagCommand("tag")),
                List.of(new StoreCategoryCommand("category")),
                List.of(new StoreCommentCommand("video_id", "username", "text"))
        );

        Set<ConstraintViolation<StoreVideoCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("width")));
    }

    @Test
    void whenHeightIsInvalid_thenConstraintViolation() {
        StoreVideoCommand command = new StoreVideoCommand(
                1920, 5000, 30, "title", "description", "username",
                "videoFileName", "thumbnailFileName",
                List.of(new StoreTagCommand("tag")),
                List.of(new StoreCategoryCommand("category")),
                List.of(new StoreCommentCommand("video_id", "username", "text"))
        );

        Set<ConstraintViolation<StoreVideoCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("height")));
    }

    @Test
    void whenDurationIsInvalid_thenConstraintViolation() {
        StoreVideoCommand command = new StoreVideoCommand(
                1920, 1080, -10, "title", "description", "username",
                "videoFileName", "thumbnailFileName",
                List.of(new StoreTagCommand("tag")),
                List.of(new StoreCategoryCommand("category")),
                List.of(new StoreCommentCommand("video_id", "username", "text"))
        );

        Set<ConstraintViolation<StoreVideoCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("duration")));
    }

    @Test
    void whenTitleIsBlank_thenConstraintViolation() {
        StoreVideoCommand command = new StoreVideoCommand(
                1920, 1080, 30, "", "description", "username",
                "videoFileName", "thumbnailFileName",
                List.of(new StoreTagCommand("tag")),
                List.of(new StoreCategoryCommand("category")),
                List.of(new StoreCommentCommand("video_id", "username", "text"))
        );

        Set<ConstraintViolation<StoreVideoCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
    }

    @Test
    void whenUsernameIsBlank_thenConstraintViolation() {
        StoreVideoCommand command = new StoreVideoCommand(
                1920, 1080, 30, "title", "description", "",
                "videoFileName", "thumbnailFileName",
                List.of(new StoreTagCommand("tag")),
                List.of(new StoreCategoryCommand("category")),
                List.of(new StoreCommentCommand("video_id", "username", "text"))
        );

        Set<ConstraintViolation<StoreVideoCommand>> violations = validator.validate(command);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")));
    }

    @Test
    void whenTagsCategoriesAndCommentsAreEmpty_thenNoConstraintViolations() {
        StoreVideoCommand command = new StoreVideoCommand(
                1920, 1080, 30, "title", "description", "username",
                "videoFileName", "thumbnailFileName",
                List.of(),
                List.of(),
                List.of());

        Set<ConstraintViolation<StoreVideoCommand>> violations = validator.validate(command);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenNonRequiredFieldsAreNull_thenNoConstraintViolations() {
        StoreVideoCommand command = new StoreVideoCommand(
                1920, 1080, 30, "title", null, "username",
                "videoFileName", "thumbnailFileName", null, null, null);

        Set<ConstraintViolation<StoreVideoCommand>> violations = validator.validate(command);
        assertTrue(violations.isEmpty());
    }
}