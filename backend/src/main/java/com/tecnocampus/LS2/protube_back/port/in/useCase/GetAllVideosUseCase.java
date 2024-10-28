package com.tecnocampus.LS2.protube_back.port.in.useCase;

import com.tecnocampus.LS2.protube_back.port.in.command.GetVideoCommand;

import java.util.List;

public interface GetAllVideosUseCase {
    List<GetVideoCommand> getAllVideos();
}
