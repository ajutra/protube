package com.tecnocampus.LS2.protube_back.port.in.useCase;

import com.tecnocampus.LS2.protube_back.port.in.command.GetCommentCommand;

import java.util.List;

public interface GetAllCommentsByVideoUseCase {
    List<GetCommentCommand> getAllCommentsByVideo(String videoId);
}
