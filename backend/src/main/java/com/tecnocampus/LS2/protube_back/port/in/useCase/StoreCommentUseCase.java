package com.tecnocampus.LS2.protube_back.port.in.useCase;

import com.tecnocampus.LS2.protube_back.port.in.command.StoreCommentCommand;

public interface StoreCommentUseCase {
    void storeComment(StoreCommentCommand storeCommentCommand);
}
