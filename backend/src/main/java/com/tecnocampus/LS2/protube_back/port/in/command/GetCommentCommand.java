package com.tecnocampus.LS2.protube_back.port.in.command;

import com.tecnocampus.LS2.protube_back.domain.model.Comment;

public record GetCommentCommand(
        String videoId,
        String commentId,
        String username,
        String text
) {
    public static GetCommentCommand from(Comment comment) {
        return new GetCommentCommand(comment.getVideoId(), comment.getId(), comment.getUsername(), comment.getText());
    }
}
