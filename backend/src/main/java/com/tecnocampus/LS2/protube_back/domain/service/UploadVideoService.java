package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.UploadVideoUseCase;
import com.tecnocampus.LS2.protube_back.port.out.StoreVideoPort;
import com.tecnocampus.LS2.protube_back.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UploadVideoService implements UploadVideoUseCase {
    private final StoreVideoPort storeVideoPort;
    private final GetUserService getUserService;

    @Value("${pro_tube.store.dir}")
    private String storageDir;

    @Override
    @Transactional
    public void storeVideoWithFiles(MultipartFile file, MultipartFile thumbnail, StoreVideoCommand storeVideoCommand) {
        User user = getUserService.getUserByUsername(storeVideoCommand.username());
        Video video = Video.from(storeVideoCommand, user);
        video.setVideoFileName(file.getOriginalFilename());
        video.setThumbnailFileName(thumbnail.getOriginalFilename());

        try {
            Path videoPath = Paths.get(storageDir, video.getVideoFileName());
            Path thumbnailPath = Paths.get(storageDir, video.getThumbnailFileName());

            Files.copy(file.getInputStream(), videoPath, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(thumbnail.getInputStream(), thumbnailPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Archivos subidos exitosamente: " + video.getVideoFileName() + ", " + video.getThumbnailFileName());
        } catch (IOException e) {
             throw new RuntimeException("Error al subir los archivos", e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado", e);
        }

        storeVideoPort.storeVideo(video, Set.of(), Set.of());
    }

    @Override
    @Transactional
    public void storeVideo(StoreVideoCommand storeVideoCommand) {
        User user = getUserService.getUserByUsername(storeVideoCommand.username());
        Video video = Video.from(storeVideoCommand, user);
        storeVideoPort.storeVideo(video, Set.of(), Set.of());
    }
}
