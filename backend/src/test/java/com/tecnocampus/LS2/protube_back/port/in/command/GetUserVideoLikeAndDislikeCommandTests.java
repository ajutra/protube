package com.tecnocampus.LS2.protube_back.port.in.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GetUserVideoLikeAndDislikeCommandTests {
    @Test
    void fromCreatesCommandWithCorrectValues() {
        GetUserVideoLikeAndDislikeCommand command = GetUserVideoLikeAndDislikeCommand.from(true, false);

        assertTrue(command.hasLiked());
        assertFalse(command.hasDisliked());
    }

    @Test
    void fromCreatesCommandWithBothValuesFalse() {
        GetUserVideoLikeAndDislikeCommand command = GetUserVideoLikeAndDislikeCommand.from(false, false);

        assertFalse(command.hasLiked());
        assertFalse(command.hasDisliked());
    }
}