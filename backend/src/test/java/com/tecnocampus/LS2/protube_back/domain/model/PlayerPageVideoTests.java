package com.tecnocampus.LS2.protube_back.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

class PlayerPageVideoTests {

    @Test
    void from_createsVideoWithAllData() {
        Video video = TestObjectFactory.createDummyVideo("1");
        List<Tag> tags = List.of(TestObjectFactory.createDummyTag("1"));
        List<Category> categories = List.of(TestObjectFactory.createDummyCategory("1"));
        List<Comment> comments = List.of(TestObjectFactory.createDummyComment("1"));

        PlayerPageVideo playerPageVideo = PlayerPageVideo.from(video, tags, categories, comments);

        assertEquals(video, playerPageVideo.video());
        assertEquals(1, playerPageVideo.tags().size());
        assertEquals(tags.getFirst(), playerPageVideo.tags().getFirst());
        assertEquals(1, playerPageVideo.categories().size());
        assertEquals(categories.getFirst(), playerPageVideo.categories().getFirst());
        assertEquals(1, playerPageVideo.comments().size());
        assertEquals(comments.getFirst(), playerPageVideo.comments().getFirst());
    }

    @Test
    void from_handlesEmptyTagsCategoriesAndComments() {
        Video video = TestObjectFactory.createDummyVideo("1");
        List<Tag> tags = List.of();
        List<Category> categories = List.of();
        List<Comment> comments = List.of();

        PlayerPageVideo playerPageVideo = PlayerPageVideo.from(video, tags, categories, comments);

        assertEquals(video, playerPageVideo.video());
        assertTrue(playerPageVideo.tags().isEmpty());
        assertTrue(playerPageVideo.categories().isEmpty());
        assertTrue(playerPageVideo.comments().isEmpty());
    }
}
