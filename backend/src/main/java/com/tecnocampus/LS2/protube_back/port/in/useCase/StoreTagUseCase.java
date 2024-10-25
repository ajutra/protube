package com.tecnocampus.LS2.protube_back.port.in.useCase;

import com.tecnocampus.LS2.protube_back.port.in.command.StoreTagCommand;

public interface StoreTagUseCase {
    void storeTag(StoreTagCommand storeTagCommand);
}
