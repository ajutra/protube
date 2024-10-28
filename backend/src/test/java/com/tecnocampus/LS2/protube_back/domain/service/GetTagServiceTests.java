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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GetTagServiceTests {

    @Mock
    private GetTagPort getTagPort;

    @InjectMocks
    private GetTagService getTagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTag_validTagName_returnsGetTagCommand() {
        String tagName = "validTag";
        Tag tag = TestObjectFactory.createDummyTag(tagName);
        GetTagCommand expectedCommand = TestObjectFactory.createDummyGetTagCommand(tagName);

        when(getTagPort.getTag(tagName)).thenReturn(tag);

        GetTagCommand result = getTagService.getTag(tagName);

        assertEquals(expectedCommand, result);
    }
}