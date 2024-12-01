package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.port.in.command.GetUserVideoLikeAndDislikeCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.EditUserVideoLikeOrDislikeUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetUserVideoLikeAndDislikeUseCase;
import com.tecnocampus.LS2.protube_back.port.out.UserVideoLikePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserVideoLikeOrDislikeService implements EditUserVideoLikeOrDislikeUseCase, GetUserVideoLikeAndDislikeUseCase {
    private final UserVideoLikePort userVideoLikePort;

    @Override
    public void likeVideo(String username, String videoId) {
        userVideoLikePort.likeVideo(username, videoId);
    }

    @Override
    public void dislikeVideo(String username, String videoId) {
        userVideoLikePort.dislikeVideo(username, videoId);
    }

    @Override
    public void removeLikeOrDislike(String username, String videoId) {
        userVideoLikePort.removeLikeOrDislike(username, videoId);
    }

    @Override
    public GetUserVideoLikeAndDislikeCommand getUserVideoLikeAndDislike(String username, String videoId) {
        Map<String, Boolean> likesAndDislikes = userVideoLikePort.getLikesAndDislikes(username, videoId);

        return GetUserVideoLikeAndDislikeCommand.from(likesAndDislikes.get("hasLiked"), likesAndDislikes.get("hasDisliked"));
    }
}
