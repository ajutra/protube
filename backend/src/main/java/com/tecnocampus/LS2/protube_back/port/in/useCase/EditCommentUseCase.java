package com.tecnocampus.LS2.protube_back.port.in.useCase;

import com.tecnocampus.LS2.protube_back.port.in.command.EditCommentCommand;

public interface EditCommentUseCase {
    void editComment(EditCommentCommand command);
}
