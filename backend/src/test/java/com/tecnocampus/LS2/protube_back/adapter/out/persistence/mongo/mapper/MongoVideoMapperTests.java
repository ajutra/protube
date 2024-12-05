package com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo.mapper;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo.document.VideoDocument;
import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MongoVideoMapperTests {
    private final MongoVideoMapper mapper = new MongoVideoMapper();

    @Test
    void convertsVideoToDocument() {
        Video video = new Video();
        video.setId("1");
        video.setTitle("Test Title");
        video.setUsername("TestUser");
        Set<Tag> tags = Set.of(new Tag("tag1"), new Tag("tag2"));
        Set<Category> categories = Set.of(new Category("category1"), new Category("category2"));

        VideoDocument document = mapper.toDocument(video, tags, categories);

        assertEquals("1", document.getId());
        assertEquals("Test Title", document.getTitle());
        assertEquals("TestUser", document.getUsername());
        assertEquals(Set.of("tag1", "tag2"), document.getTags());
        assertEquals(Set.of("category1", "category2"), document.getCategories());
    }

    @Test
    void convertsDocumentToVideo() {
        VideoDocument document = new VideoDocument(
                "1",
                "Test Title",
                "TestUser",
                Set.of("tag1", "tag2"),
                Set.of("category1", "category2")
        );

        Video video = mapper.toDomain(document);

        assertEquals("1", video.getId());
        assertEquals("Test Title", video.getTitle());
        assertEquals("TestUser", video.getUsername());
    }

    @Test
    void convertsVideoToDocumentWithEmptyTagsAndCategories() {
        Video video = new Video();
        video.setId("1");
        video.setTitle("Test Title");
        video.setUsername("TestUser");
        Set<Tag> tags = Set.of();
        Set<Category> categories = Set.of();

        VideoDocument document = mapper.toDocument(video, tags, categories);

        assertEquals("1", document.getId());
        assertEquals("Test Title", document.getTitle());
        assertEquals("TestUser", document.getUsername());
        assertEquals(Set.of(), document.getTags());
        assertEquals(Set.of(), document.getCategories());
    }

    @Test
    void convertsDocumentToVideoWithEmptyTagsAndCategories() {
        VideoDocument document = new VideoDocument(
                "1",
                "Test Title",
                "TestUser",
                Set.of(),
                Set.of()
        );

        Video video = mapper.toDomain(document);

        assertEquals("1", video.getId());
        assertEquals("Test Title", video.getTitle());
        assertEquals("TestUser", video.getUsername());
    }
}