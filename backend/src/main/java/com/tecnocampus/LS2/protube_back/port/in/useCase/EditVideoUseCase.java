package com.tecnocampus.LS2.protube_back.port.in.useCase;

import com.tecnocampus.LS2.protube_back.port.in.command.EditVideoCommand;

public interface EditVideoUseCase {
    void editVideo(EditVideoCommand command);
}
