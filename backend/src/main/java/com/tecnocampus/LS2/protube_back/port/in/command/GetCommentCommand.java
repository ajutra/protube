package com.tecnocampus.LS2.protube_back.port.in.command;

import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import com.tecnocampus.LS2.protube_back.domain.model.Video;

import java.util.List;

public record GetCommentCommand(
        String videoId,
        String username,
        String text
) {
    public static List<GetCommentCommand> from(List<Comment> comments) {
        //Don't worry about the Snake_Case naming
        return comments.stream()
                .map(comment -> new GetCommentCommand(comment.getVideo_id(), comment.getUsername(), comment.getText()))
                .toList();
    }
}
