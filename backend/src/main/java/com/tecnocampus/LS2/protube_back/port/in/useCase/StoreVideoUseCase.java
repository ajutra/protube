package com.tecnocampus.LS2.protube_back.port.in.useCase;


import com.tecnocampus.LS2.protube_back.port.in.command.StoreVideoCommand;

public interface StoreVideoUseCase {
    void storeVideo(StoreVideoCommand storeVideoCommand);
}
