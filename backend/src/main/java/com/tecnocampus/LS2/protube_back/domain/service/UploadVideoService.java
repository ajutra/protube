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

        Path videoPath = Paths.get(storageDir, video.getVideoFileName());
        Path thumbnailPath = Paths.get(storageDir, video.getThumbnailFileName());

        try {
            // Check if files already exist and change names if necessary
            videoPath = resolveFileNameConflict(videoPath);
            thumbnailPath = resolveFileNameConflict(thumbnailPath);

            // Store video file
            Files.copy(file.getInputStream(), videoPath);

            // Store thumbnail file
            Files.copy(thumbnail.getInputStream(), thumbnailPath);

            // Update video file names with new paths
            video.setVideoFileName(videoPath.getFileName().toString());
            video.setThumbnailFileName(thumbnailPath.getFileName().toString());

            // Store video metadata in the database
            storeVideoPort.storeVideo(video, Set.of(), Set.of());

        } catch (IOException e) {
            // Cleanup files if there's an error
            try {
                Files.deleteIfExists(videoPath);
                Files.deleteIfExists(thumbnailPath);
            } catch (IOException cleanupException) {
                throw new RuntimeException("Error cleaning up files after upload failure", cleanupException);
            }
            throw new RuntimeException("Error uploading files", e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error", e);
        }
    }

    private Path resolveFileNameConflict(Path path) {
        Path resolvedPath = path;
        int counter = 1;
        while (Files.exists(resolvedPath)) {
            String newFileName = String.format("%s_%d%s",
                    stripExtension(path.getFileName().toString()),
                    counter++,
                    getFileExtension(path.getFileName().toString()));
            resolvedPath = path.getParent().resolve(newFileName);
        }
        return resolvedPath;
    }

    private String stripExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex == -1) ? fileName : fileName.substring(0, lastDotIndex);
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex == -1) ? "" : fileName.substring(lastDotIndex);
    }
}
