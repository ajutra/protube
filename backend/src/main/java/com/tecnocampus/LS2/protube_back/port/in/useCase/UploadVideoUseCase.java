package com.tecnocampus.LS2.protube_back.port.in.useCase;

import org.springframework.web.multipart.MultipartFile;

public interface UploadVideoUseCase {
    void uploadVideo(MultipartFile file, String title, String description, String username);
}
