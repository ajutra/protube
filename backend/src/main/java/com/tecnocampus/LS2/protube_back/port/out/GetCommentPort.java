package com.tecnocampus.LS2.protube_back.port.out;

import com.tecnocampus.LS2.protube_back.domain.model.Comment;

import java.util.List;

public interface GetCommentPort {
    List<Comment> getAllCommentsByVideoId(String videoId);

    List<Comment> getCommentsByUsername(String username);
}
