package com.tecnocampus.LS2.protube_back.port.in.useCase;

import com.tecnocampus.LS2.protube_back.port.in.command.StoreVideoCommand;
import org.springframework.web.multipart.MultipartFile;

public interface StoreVideoUseCase {
    void storeVideoWithFiles(MultipartFile file, MultipartFile thumbnail, StoreVideoCommand storeVideoCommand);
}
