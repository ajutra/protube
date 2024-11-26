package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.User;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.useCase.UploadVideoUseCase;
import com.tecnocampus.LS2.protube_back.port.out.FileUploadService;
import com.tecnocampus.LS2.protube_back.port.out.StoreVideoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UploadVideoService implements UploadVideoUseCase {
    private final FileUploadService fileUploadService;
    private final StoreVideoPort storeVideoPort;
    private final GetUserService getUserService;

    @Override
    @Transactional
    public void uploadVideo(MultipartFile file, String title, String description, String username) {
        User user = getUserService.getUserByUsername(username);
        Video video = new Video();
        video.setTitle(title);
        video.setDescription(description);
        video.setUsername(username);
        video.setVideoFileName(file.getOriginalFilename());
        video.setThumbnailFileName(title + username + ".webp");

        try {
            fileUploadService.uploadFile(file.getInputStream(), video.getVideoFileName());
        } catch (IOException e) {
            throw new RuntimeException("Error al subir el archivo", e);
        }

        storeVideoPort.storeVideo(video, Set.of(), Set.of());
    }
}
