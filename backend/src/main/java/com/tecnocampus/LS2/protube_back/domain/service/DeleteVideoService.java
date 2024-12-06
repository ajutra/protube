package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.port.in.command.GetVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.DeleteVideoUseCase;
import com.tecnocampus.LS2.protube_back.port.out.DeleteVideoPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DeleteVideoService implements DeleteVideoUseCase {
    private final DeleteVideoPort deleteVideoPort;
    private final DeleteVideoPort searchDbDeleteVideoPort;
    private final GetVideoService getVideoService;

    @Value("${pro_tube.store.dir}")
    private String storageDir;

    public DeleteVideoService(
            @Qualifier("postgresVideoPort") DeleteVideoPort deleteVideoPort,
            @Qualifier("mongoVideoPort") DeleteVideoPort searchDbDeleteVideoPort,
            GetVideoService getVideoService) {
        this.deleteVideoPort = deleteVideoPort;
        this.searchDbDeleteVideoPort = searchDbDeleteVideoPort;
        this.getVideoService = getVideoService;
    }

    @Override
    @Transactional
    public void deleteVideo(String videoId) {
        GetVideoCommand getVideoCommand = getVideoService.getVideoById(videoId);
        Path videoPath = Paths.get(storageDir, getVideoCommand.videoFileName());
        Path thumbnailPath = Paths.get(storageDir, getVideoCommand.thumbnailFileName());

        try {
            Files.delete(videoPath);
            Files.delete(thumbnailPath);
            deleteVideoPort.deleteVideo(videoId);
            searchDbDeleteVideoPort.deleteVideo(videoId);

        } catch (IOException e) {
            throw new RuntimeException("Error deleting video with id: " + videoId + ": " + e.getMessage());
        }
    }
}
