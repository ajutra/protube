package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.port.in.command.GetTagCommand;
import com.tecnocampus.LS2.protube_back.port.out.GetTagPort;
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

public class GetAllTagsServiceTests {

    @Mock
    private GetTagPort getTagPort;

    @InjectMocks
    private GetAllTagsService getAllTagsService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTagsReturnsListOfTags(){
        Tag tag1 = TestObjectFactory.createDummyTag("1");
        List<Tag> tags = List.of(tag1);
        List<GetTagCommand> tagCommands = tags.stream().map(GetTagCommand::from).toList();;

        when(getTagPort.getAllTags()).thenReturn(tags);

        List<GetTagCommand> result = getAllTagsService.getAllTags();

        assertEquals(1, result.size());
        assertEquals(tagCommands.getFirst().tagName(), result.getFirst().tagName());
        verify(getTagPort).getAllTags();
    }

    @Test
    void getAllTagsReturnsEmptyListWhenNoTags(){
        when(getTagPort.getAllTags()).thenReturn(List.of());

        List<GetTagCommand> result = getAllTagsService.getAllTags();

        assertTrue(result.isEmpty());
    }
}
