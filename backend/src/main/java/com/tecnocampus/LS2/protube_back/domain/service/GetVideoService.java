package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.out.GetVideosPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetVideoService {
    private final GetVideosPort getVideosPort;

    Video getVideoByTitleAndUsername(String title, String username) {
        return getVideosPort.getVideoByTitleAndUsername(title, username);
    }
}
