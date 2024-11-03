package com.tecnocampus.LS2.protube_back.domain.model;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreTagCommand;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagTests {

    @Test
    void fromCommandCreatesTagWithCorrectName() {
        StoreTagCommand command = TestObjectFactory.createDummyStoreTagCommand("tag_name");
        Tag tag = Tag.from(command);
        assertEquals(command.tagName(), tag.name());
    }
}