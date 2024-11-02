package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import com.tecnocampus.LS2.protube_back.port.in.command.GetCommentCommand;
import com.tecnocampus.LS2.protube_back.port.out.GetCommentPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetAllCommentsServiceTests {

    @Mock
    private GetCommentPort getCommentPort;

    @InjectMocks
    private GetAllCommentsService getCommentsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCommentsByUsernameReturnsListOfComments() {
        String username = "Username 1";
        Comment comment1 = TestObjectFactory.createDummyComment("1", TestObjectFactory.createDummyUser(username), TestObjectFactory.createDummyVideo("video1"));
        Comment comment2 = TestObjectFactory.createDummyComment("2", TestObjectFactory.createDummyUser(username), TestObjectFactory.createDummyVideo("video2"));

        List<Comment> comments = List.of(comment1, comment2);
        List<GetCommentCommand> commentCommands =
                comments.stream()
                        .map(GetCommentCommand::from)
                        .toList();

        when(getCommentPort.getCommentsByUsername(username)).thenReturn(comments);

        List<GetCommentCommand> result = getCommentsService.getCommentsByUsername(username);

        assertEquals(2, result.size());
        assertEquals(commentCommands.get(0).text(), result.get(0).text());
        assertEquals(commentCommands.get(1).text(), result.get(1).text());
        verify(getCommentPort).getCommentsByUsername(username);
    }

    @Test
    void getCommentsByUsernameReturnsEmptyListWhenNoComments() {
        String username = "Username 2";
        when(getCommentPort.getCommentsByUsername(username)).thenReturn(List.of());

        List<GetCommentCommand> result = getCommentsService.getCommentsByUsername(username);

        assertTrue(result.isEmpty());
        verify(getCommentPort).getCommentsByUsername(username);
    }
}
