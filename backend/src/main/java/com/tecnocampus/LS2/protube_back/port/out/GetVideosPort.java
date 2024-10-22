package com.tecnocampus.LS2.protube_back.port.out;

import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.command.GetVideoCommand;

import java.util.List;

public interface GetVideosPort {
    List<Video> getAllVideos();
    Video getVideoByTitleAndUsername(String title, String username);
}
