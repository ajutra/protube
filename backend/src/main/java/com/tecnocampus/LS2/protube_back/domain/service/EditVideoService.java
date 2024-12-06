package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.command.EditVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.EditVideoUseCase;
import com.tecnocampus.LS2.protube_back.port.out.StoreVideoPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditVideoService implements EditVideoUseCase {
    private final StoreVideoPort storeVideoPort;
    private final StoreVideoPort searchDbStoreVideoPort;

    public EditVideoService(
            @Qualifier("postgresVideoPort") StoreVideoPort storeVideoPort,
            @Qualifier("mongoVideoPort") StoreVideoPort searchDbStoreVideoPort) {
        this.storeVideoPort = storeVideoPort;
        this.searchDbStoreVideoPort = searchDbStoreVideoPort;
    }

    @Transactional
    public void editVideo(EditVideoCommand command) {
        storeVideoPort.editVideo(Video.from(command));
        searchDbStoreVideoPort.editVideo(Video.from(command));
    }
}
