package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import com.tecnocampus.LS2.protube_back.port.in.command.EditCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.EditCommentUseCase;
import com.tecnocampus.LS2.protube_back.port.out.StoreCommentPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditCommentService implements EditCommentUseCase {
    private final StoreCommentPort storeCommentPort;

    public void editComment(EditCommentCommand command) {
        storeCommentPort.editComment(Comment.from(command));
    }
}
