package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreUserCommand;
import com.tecnocampus.LS2.protube_back.port.out.StoreCommentPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreCommentService {
    private final StoreCommentPort storeCommentPort;
    private final StoreUserService storeUserService;

    // This method is intended to be used only by the StoreVideoService class when loading initial data
    public void storeCommentFromStoreVideoService(StoreCommentCommand command, Video video) {
        try {
            storeUserService.storeUser(StoreUserCommand.from(command.username()));
        } catch (IllegalArgumentException ignored) {
            // User already exists
        }

        Comment comment = Comment.from(command);
        comment.setVideoId(video.getId());

        storeCommentPort.storeComment(comment);
    }
}
