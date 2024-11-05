package com.tecnocampus.LS2.protube_back.port.in.command;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetCommentCommandTests {

    @Test
    void from() {
        Comment comment = TestObjectFactory.createDummyComment("test");
        GetCommentCommand command = GetCommentCommand.from(comment);

        assertEquals(comment.getVideoId(), command.videoId());
        assertEquals(comment.getUsername(), command.username());
        assertEquals(comment.getText(), command.text());
    }
}
