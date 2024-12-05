package com.tecnocampus.LS2.protube_back.port.out;

import java.util.Map;

public interface UserVideoLikePort {
    void likeVideo(String username, String videoId);
    void dislikeVideo(String username, String videoId);
    void removeLikeOrDislike(String username, String videoId);
    Map<String, Boolean> getLikesAndDislikes(String username, String videoId);
}
