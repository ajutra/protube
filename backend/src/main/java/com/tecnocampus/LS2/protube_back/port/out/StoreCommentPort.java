package com.tecnocampus.LS2.protube_back.port.out;

import com.tecnocampus.LS2.protube_back.domain.model.Comment;

public interface StoreCommentPort {
    void storeComment(Comment comment);
    void editComment(Comment comment);
}
