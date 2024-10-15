package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.domain.model.VideoTitle;
import com.tecnocampus.LS2.protube_back.port.in.GetAllVideosNamesUseCase;
import com.tecnocampus.LS2.protube_back.port.out.GetVideosPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllVideosNamesService implements GetAllVideosNamesUseCase {
    private final GetVideosPort getVideosPort;

    @Override
    public List<VideoTitle> getAllVideos() {
        List<Video> videos = getVideosPort.getAllVideos();

        if (videos == null)
            return List.of();

        else
            return getVideosPort.getAllVideos().stream()
                    .map(VideoTitle::fromVideo)
                    .collect(java.util.stream.Collectors.toList());
    }
}
