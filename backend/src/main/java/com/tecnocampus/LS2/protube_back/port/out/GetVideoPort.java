package com.tecnocampus.LS2.protube_back.port.out;

import com.tecnocampus.LS2.protube_back.domain.model.Field;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.domain.model.PlayerPageVideo;

import java.util.List;
import java.util.Set;

public interface GetVideoPort {
    List<Video> getAllVideos(); // I left this method in case it's needed in the future
    Video getVideoByTitleAndUsername(String title, String username);
    void checkIfVideoExists (String videoId);
    List<PlayerPageVideo> getAllVideosWithFields(Set<Field> fields);
}
