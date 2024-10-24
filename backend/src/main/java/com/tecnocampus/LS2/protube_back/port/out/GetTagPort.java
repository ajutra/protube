package com.tecnocampus.LS2.protube_back.port.out;

import com.tecnocampus.LS2.protube_back.domain.model.Tag;

import java.util.List;

public interface GetTagPort {
    List<Tag> getAllTags();
}
