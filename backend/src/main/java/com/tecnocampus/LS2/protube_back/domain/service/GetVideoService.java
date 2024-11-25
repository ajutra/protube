package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.out.GetVideoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetVideoService {
    private final GetVideoPort getVideoPort;

    Video getVideoByTitleAndUsername(String title, String username) {
        return getVideoPort.getVideoByTitleAndUsername(title, username);
    }

    public Video getVideoById(String videoId) {
        getVideoPort.checkIfVideoExists(videoId);
        return getVideoPort.getVideoById(videoId);
    }
}
