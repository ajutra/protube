package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.port.in.useCase.DeleteVideoUseCase;
import com.tecnocampus.LS2.protube_back.port.out.DeleteVideoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteVideoService implements DeleteVideoUseCase {
    private final DeleteVideoPort deleteVideoPort;

    @Override
    public void deleteVideo(String videoId) {
        deleteVideoPort.deleteVideo(videoId);
    }
}
