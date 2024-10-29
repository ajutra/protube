package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreTagCommand;
import com.tecnocampus.LS2.protube_back.port.out.StoreTagPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StoreTagServiceTests {

    @Mock
    private StoreTagPort storeTagPort;

    @InjectMocks
    private StoreTagService storeTagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void storeAndGetTagTest(){
        StoreTagCommand command = TestObjectFactory.createDummyStoreTagCommand("1");
        Tag expectedTag = TestObjectFactory.createDummyTag("1");

        when(storeTagPort.storeAndGetTag(any(Tag.class))).thenReturn(expectedTag);

        Tag result = storeTagService.storeAndGetTag(command);

        verify(storeTagPort).storeAndGetTag(any(Tag.class));
        assertEquals(expectedTag, result);
    }

    @Test
    void storeTagTest(){
        StoreTagCommand command = TestObjectFactory.createDummyStoreTagCommand("1");

        storeTagService.storeTag(command);

        verify(storeTagPort).storeTag(any(Tag.class));

    }
}
