package com.tecnocampus.LS2.protube_back.port.in.useCase;

import com.tecnocampus.LS2.protube_back.port.in.command.UpdateVideoCommand;

public interface EditVideoUseCase {
    void editVideo(UpdateVideoCommand updateVideoCommand, String videoId);
}
