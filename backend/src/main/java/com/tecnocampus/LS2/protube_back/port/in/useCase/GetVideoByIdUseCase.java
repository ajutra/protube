package com.tecnocampus.LS2.protube_back.port.in.useCase;

import com.tecnocampus.LS2.protube_back.port.in.command.GetVideoCommand;

public interface GetVideoByIdUseCase {
    GetVideoCommand getVideoById(String id);
}
