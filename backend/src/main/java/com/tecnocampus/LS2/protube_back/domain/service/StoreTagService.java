package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreTagCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreTagUseCase;
import com.tecnocampus.LS2.protube_back.port.out.StoreTagPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreTagService implements StoreTagUseCase {
    private final StoreTagPort storeTagPort;

    Tag storeAndGetTag(StoreTagCommand command) {
        return storeTagPort.storeAndGetTag(Tag.from(command));
    }

    @Override
    public void storeTag(StoreTagCommand storeTagCommand) {
        storeTagPort.storeTag(Tag.from(storeTagCommand));
    }
}
