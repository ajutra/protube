package com.tecnocampus.LS2.protube_back.port.in.useCase;

import com.tecnocampus.LS2.protube_back.port.in.command.GetUserVideoLikeAndDislikeCommand;

public interface GetUserVideoLikeAndDislikeUseCase {
    GetUserVideoLikeAndDislikeCommand getUserVideoLikeAndDislike(String username, String videoId);
}
