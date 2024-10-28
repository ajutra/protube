package com.tecnocampus.LS2.protube_back.port.out;

import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.domain.model.Video;

import java.util.Set;

public interface StoreVideoPort {
    void storeVideo(Video video, Set<Tag> tags, Set<Category> categories);
    Video storeAndGetVideo(Video video, Set<Tag> tags, Set<Category> categories);
    void checkIfVideoExists (String videoId);
}
