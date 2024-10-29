package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreUserCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreCommentUseCase;
import com.tecnocampus.LS2.protube_back.port.out.StoreCommentPort;
import com.tecnocampus.LS2.protube_back.port.out.StoreUserPort;
import com.tecnocampus.LS2.protube_back.port.out.StoreVideoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreCommentService implements StoreCommentUseCase {
    private final StoreCommentPort storeCommentPort;
    private final StoreUserService storeUserService;

    private final StoreUserPort storeUserPort;
    private final StoreVideoPort storeVideoPort;

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

    @Override
    public void storeComment(StoreCommentCommand storeCommentCommand) {
        storeUserPort.checkIfUserExists(storeCommentCommand.username());
        storeVideoPort.checkIfVideoExists(storeCommentCommand.videoId());

        Comment comment = Comment.from(storeCommentCommand);
        storeCommentPort.storeComment(comment);
    }

}
