package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllVideosUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetVideoController {
    private final GetAllVideosUseCase getAllVideosUseCase;

    @GetMapping("/videos")
    public List<Video> getAllVideos() {
        return getAllVideosUseCase.getAllVideos();
    }
}
