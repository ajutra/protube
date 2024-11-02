package com.tecnocampus.LS2.protube_back.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

class VideoWithAllDataTests {

    @Test
    void from_createsVideoWithAllData() {
        Video video = TestObjectFactory.createDummyVideo("1");
        List<Tag> tags = List.of(TestObjectFactory.createDummyTag("1"));
        List<Category> categories = List.of(TestObjectFactory.createDummyCategory("1"));
        List<Comment> comments = List.of(TestObjectFactory.createDummyComment("1"));

        VideoWithAllData videoWithAllData = VideoWithAllData.from(video, tags, categories, comments);

        assertEquals(video, videoWithAllData.video());
        assertEquals(1, videoWithAllData.tags().size());
        assertEquals(tags.getFirst(), videoWithAllData.tags().getFirst());
        assertEquals(1, videoWithAllData.categories().size());
        assertEquals(categories.getFirst(), videoWithAllData.categories().getFirst());
        assertEquals(1, videoWithAllData.comments().size());
        assertEquals(comments.getFirst(), videoWithAllData.comments().getFirst());
    }

    @Test
    void from_handlesEmptyTagsCategoriesAndComments() {
        Video video = TestObjectFactory.createDummyVideo("1");
        List<Tag> tags = List.of();
        List<Category> categories = List.of();
        List<Comment> comments = List.of();

        VideoWithAllData videoWithAllData = VideoWithAllData.from(video, tags, categories, comments);

        assertEquals(video, videoWithAllData.video());
        assertTrue(videoWithAllData.tags().isEmpty());
        assertTrue(videoWithAllData.categories().isEmpty());
        assertTrue(videoWithAllData.comments().isEmpty());
    }
}
