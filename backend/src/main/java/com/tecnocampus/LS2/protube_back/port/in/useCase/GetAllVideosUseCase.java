package com.tecnocampus.LS2.protube_back.port.in.useCase;

import com.tecnocampus.LS2.protube_back.domain.model.Video;

import java.util.List;

public interface GetAllVideosUseCase {
    List<Video> getAllVideos();
}
