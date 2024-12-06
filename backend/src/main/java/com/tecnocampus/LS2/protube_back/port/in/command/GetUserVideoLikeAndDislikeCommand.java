package com.tecnocampus.LS2.protube_back.port.in.command;

public record GetUserVideoLikeAndDislikeCommand(
        boolean hasLiked,
        boolean hasDisliked
) {
    public static GetUserVideoLikeAndDislikeCommand from(boolean hasLiked, boolean hasDisliked) {
        return new GetUserVideoLikeAndDislikeCommand(hasLiked, hasDisliked);
    }
}
