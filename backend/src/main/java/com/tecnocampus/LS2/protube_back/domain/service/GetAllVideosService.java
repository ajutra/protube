package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.command.GetVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllVideosUseCase;
import com.tecnocampus.LS2.protube_back.port.out.GetVideoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAllVideosService implements GetAllVideosUseCase {
    private final GetVideoPort getVideoPort;

    @Override
    public List<GetVideoCommand> getAllVideos() {
        //TODO add tags, comments and categories
        List<Video> videos = getVideoPort.getAllVideos();

        return videos.stream()
                .map(video -> GetVideoCommand.from(video,
                                                   List.of(),
                                                   List.of(),
                                                   List.of()))
                .collect(Collectors.toList());

    }
}
