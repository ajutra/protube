package com.tecnocampus.LS2.protube_back.domain.model;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCommentCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CommentTests {

    @Test
    void from() {
        StoreCommentCommand command = TestObjectFactory.createDummyStoreCommentCommand("test");
        Comment comment = Comment.from(command);

        assertNull(comment.getId());
        assertEquals(comment.getVideoId(), command.videoId());
        assertEquals(comment.getUsername(), command.username());
        assertEquals(comment.getText(), command.text());
    }
}
