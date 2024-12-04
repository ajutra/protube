package com.tecnocampus.LS2.protube_back.port.in.command;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchVideoResultCommandTests {
    @Test
    void searchVideoResultCommandFromVideoCreatesCorrectCommand() {
        Video video = TestObjectFactory.createDummyVideo("1");
        SearchVideoResultCommand command = SearchVideoResultCommand.from(video);

        assertEquals(video.getId(), command.id());
        assertEquals(video.getTitle(), command.title());
    }
}
