package com.tecnocampus.LS2.protube_back.domain.model;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreVideoCommand;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VideoTests {

    @Test
    void fromCommandCreatesVideoWithCorrectValues() {
        StoreVideoCommand command = TestObjectFactory.createDummyStoreVideoCommand("test");
        User user = TestObjectFactory.createDummyUser("test");
        Video video = Video.from(command, user);

        assertEquals(command.title(), video.getTitle());
        assertEquals(command.width(), video.getWidth());
        assertEquals(command.height(), video.getHeight());
        assertEquals(command.duration(), video.getDuration());
        assertEquals(command.description(), video.getDescription());
        assertEquals(command.videoFileName(), video.getVideoFileName());
        assertEquals(command.thumbnailFileName(), video.getThumbnailFileName());
        assertEquals(user.username(), video.getUsername());
    }

    @Test
    void fromCommandCreatesVideoWithNullDescription() {
        User user = TestObjectFactory.createDummyUser("username");
        StoreVideoCommand command = new StoreVideoCommand(
                1920,
                1080,
                300,
                "title",
                null,
                user.username(),
                "video.mp4",
                "thumbnail.webp",
                List.of(), List.of(), List.of());
        Video video = Video.from(command, user);

        assertEquals("", video.getDescription());
    }

    @Test
    void fromCommandCreatesVideoWithNullVideoFileName() {
        User user = TestObjectFactory.createDummyUser("username");
        StoreVideoCommand command = new StoreVideoCommand(
                1920,
                1080,
                300,
                "title",
                "description",
                user.username(),
                null,
                "thumbnail.webp",
                List.of(), List.of(), List.of());
        Video video = Video.from(command, user);

        assertEquals(command.title() + command.username() + ".mp4", video.getVideoFileName());
    }

    @Test
    void fromCommandCreatesVideoWithNullThumbnailFileName() {
        User user = TestObjectFactory.createDummyUser("username");
        StoreVideoCommand command = new StoreVideoCommand(
                1920,
                1080,
                300,
                "title",
                "description",
                user.username(),
                "video.mp4",
                null,
                List.of(), List.of(), List.of());
        Video video = Video.from(command, user);

        assertEquals(command.title() + command.username() + ".webp", video.getThumbnailFileName());
    }
}
