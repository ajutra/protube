package com.tecnocampus.LS2.protube_back.port.in.useCase;


import com.tecnocampus.LS2.protube_back.port.in.command.StoreVideoCommand;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StoreVideoUseCase {
    void storeVideo(StoreVideoCommand storeVideoCommand);
}
