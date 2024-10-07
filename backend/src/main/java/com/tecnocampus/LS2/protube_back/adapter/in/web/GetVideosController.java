package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.domain.model.VideoTitle;
import com.tecnocampus.LS2.protube_back.port.in.GetVideosUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetVideosController {
    private final GetVideosUseCase getVideosUseCase;

    @GetMapping("/videos")
    public List<VideoTitle> getAllVideos() {
        return getVideosUseCase.getAllVideos();
    }
}
