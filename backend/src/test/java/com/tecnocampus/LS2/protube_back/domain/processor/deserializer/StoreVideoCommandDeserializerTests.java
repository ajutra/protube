package com.tecnocampus.LS2.protube_back.domain.processor.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreVideoCommand;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class StoreVideoCommandDeserializerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final StoreVideoCommandDeserializer deserializer = new StoreVideoCommandDeserializer();

    @Test
    void deserializeValidJson() throws IOException {
        String json = """
        {
            "width": 1920,
            "height": 1080,
            "duration": 300.0,
            "title": "Sample Video",
            "user": "test user",
            "id": 123,
            "meta": {
                "description": "A sample video description",
                "tags": ["tag1", "tag2"],
                "categories": ["cat1", "cat2"],
                "comments": [
                    {"text": "Great video!", "author": "user1"},
                    {"text": "Nice work!", "author": "user2"}
                ]
            }
        }
        """;

        JsonParser parser = objectMapper.createParser(json);
        StoreVideoCommand command = deserializer.deserialize(parser, null);

        assertEquals(1920, command.width());
        assertEquals(1080, command.height());
        assertEquals(300, command.duration());
        assertEquals("Sample Video", command.title());
        assertEquals("test user", command.username());
        assertEquals("123.mp4", command.videoFileName());
        assertEquals("123.webp", command.thumbnailFileName());
        assertEquals("A sample video description", command.description());
        assertEquals(2, command.tags().size());
        assertEquals(2, command.categories().size());
        assertEquals(2, command.comments().size());
    }

    @Test
    void deserializeMissingFields() throws IOException {
        String json = """
        {
            "width": 1920,
            "height": 1080,
            "duration": 300,
            "title": "Sample Video",
            "user": "test user",
            "id": 123
        }
        """;

        JsonParser parser = objectMapper.createParser(json);
        StoreVideoCommand command = deserializer.deserialize(parser, null);

        assertEquals(1920, command.width());
        assertEquals(1080, command.height());
        assertEquals(300, command.duration());
        assertEquals("Sample Video", command.title());
        assertEquals("test user", command.username());
        assertEquals("123.mp4", command.videoFileName());
        assertEquals("123.webp", command.thumbnailFileName());
        assertTrue(command.description().isEmpty());
        assertTrue(command.tags().isEmpty());
        assertTrue(command.categories().isEmpty());
        assertTrue(command.comments().isEmpty());
    }

    @Test
    void deserializeInvalidJson() throws IOException {
        String json = """
        {
            "meta": {
                "description": "A sample video description",
                "tags": ["tag1", "tag2"],
                "categories": ["cat1", "cat2"],
                "comments": [
                    {"text": "Great video!", "author": "user1"},
                    {"text": "Nice work!", "author": "user2"}
                ]
            }
        }
        """;

        try (JsonParser parser = objectMapper.createParser(json)) {
            assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize(parser, null));
        }
    }
}