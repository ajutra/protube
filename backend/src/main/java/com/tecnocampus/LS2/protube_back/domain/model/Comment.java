package com.tecnocampus.LS2.protube_back.domain.model;

import com.tecnocampus.LS2.protube_back.port.in.command.StoreCommentCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Comment {
    private String id;
    private String videoId;
    private String username;
    private String text;

    public static Comment from(StoreCommentCommand command) {
        return new Comment(null, command.videoId(), command.username(), command.text());
    }
}
