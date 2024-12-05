package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.port.in.useCase.DeleteVideoUseCase;
import com.tecnocampus.LS2.protube_back.port.out.DeleteVideoPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DeleteVideoService implements DeleteVideoUseCase {
    private final DeleteVideoPort deleteVideoPort;
    private final DeleteVideoPort searchDbDeleteVideoPort;

    public DeleteVideoService(
            @Qualifier("postgresVideoPort") DeleteVideoPort deleteVideoPort,
            @Qualifier("mongoVideoPort") DeleteVideoPort searchDbDeleteVideoPort) {
        this.deleteVideoPort = deleteVideoPort;
        this.searchDbDeleteVideoPort = searchDbDeleteVideoPort;
    }

    @Override
    public void deleteVideo(String videoId) {
        deleteVideoPort.deleteVideo(videoId);
        searchDbDeleteVideoPort.deleteVideo(videoId);
    }
}
