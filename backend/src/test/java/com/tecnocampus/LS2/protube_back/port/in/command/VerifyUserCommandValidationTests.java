package com.tecnocampus.LS2.protube_back.port.in.command;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class VerifyUserCommandValidationTests {
    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void verifyUserWithBlankUsername() {
        VerifyUserCommand command = new VerifyUserCommand("", "validPassword");

        Set<ConstraintViolation<VerifyUserCommand >> violations = validator.validate(command);
        assertEquals(1, violations.size());
    }

    @Test
    void verifyUserWithBlankPassword() {
        VerifyUserCommand command = new VerifyUserCommand("validUsername", "");

        Set<ConstraintViolation<VerifyUserCommand >> violations = validator.validate(command);
        assertEquals(1, violations.size());
    }

    @Test
    void verifyUserWithNullUsername() {
        VerifyUserCommand command = new VerifyUserCommand(null, "validPassword");

        Set<ConstraintViolation<VerifyUserCommand >> violations = validator.validate(command);
        assertEquals(1, violations.size());
    }

    @Test
    void verifyUserWithNullPassword() {
        VerifyUserCommand command = new VerifyUserCommand("validUsername", null);

        Set<ConstraintViolation<VerifyUserCommand >> violations = validator.validate(command);
        assertEquals(1, violations.size());
    }
}
