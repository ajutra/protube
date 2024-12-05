package com.tecnocampus.LS2.protube_back.port.in.useCase;

public interface EditUserVideoLikeOrDislikeUseCase {
    void likeVideo(String username, String videoId);
    void dislikeVideo(String username, String videoId);
    void removeLikeOrDislike(String username, String videoId);
}
