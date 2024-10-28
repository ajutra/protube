package com.tecnocampus.LS2.protube_back.port.out;

import com.tecnocampus.LS2.protube_back.domain.model.Tag;

public interface GetTagPort {
    Tag getTag(String tagName);
}
