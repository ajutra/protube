package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.domain.model.VideoTitle;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllVideosUseCase;
import com.tecnocampus.LS2.protube_back.port.out.GetVideosPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllVideosService implements GetAllVideosUseCase {
    private final GetVideosPort getVideosPort;

    @Override
    public List<Video> getAllVideos() {
        List<Video> videos = getVideosPort.getAllVideos();

        if (videos == null)
            return List.of();

        else
            return getVideosPort.getAllVideos();
    }
}
