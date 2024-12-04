package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.command.EditVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.EditVideoUseCase;
import com.tecnocampus.LS2.protube_back.port.out.StoreVideoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditVideoService implements EditVideoUseCase {
    private final StoreVideoPort storeVideoPort;

    public void editVideo(EditVideoCommand command) {
        storeVideoPort.editVideo(Video.from(command));
    }
}
