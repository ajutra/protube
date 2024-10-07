package com.tecnocampus.LS2.protube_back.port.in;

import com.tecnocampus.LS2.protube_back.domain.model.VideoTitle;

import java.util.List;

public interface GetVideosUseCase {
    List<VideoTitle> getAllVideos();
}
