package com.tecnocampus.LS2.protube_back.domain.model;

import com.tecnocampus.LS2.protube_back.port.in.command.EditCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCommentCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
public class Comment {
    private String id;
    private String videoId;
    private String username;
    private String text;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id)
                && Objects.equals(videoId, comment.videoId)
                && Objects.equals(username, comment.username)
                && Objects.equals(text, comment.text);
    }

    public static Comment from(StoreCommentCommand command) {
        return new Comment(null, command.videoId(), command.username(), command.text());
    }

    public static Comment from(EditCommentCommand command) {
        return new Comment(command.commentId(), null, null, command.text());
    }
}
