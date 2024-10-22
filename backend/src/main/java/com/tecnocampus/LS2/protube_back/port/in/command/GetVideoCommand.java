package com.tecnocampus.LS2.protube_back.port.in.command;

import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.domain.model.Video;

import java.util.List;

public record GetVideoCommand(
        String videoId,
        int width,
        int height,
        int duration,
        String title,
        String username,
        String videoFileName,
        String thumbnailFileName,
        Meta meta
) {
    public record Meta(
            String description,
            List<GetCategoryCommand> categories,
            List<GetTagCommand> tags,
            List<GetCommentCommand> comments
    ) {
    }


    public static GetVideoCommand from(Video video, List<Category> categories, List<Tag> tags, List<Comment> comments) {
        return new GetVideoCommand(video.getId(),
                                   video.getWidth(),
                                   video.getHeight(),
                                   video.getDuration(),
                                   video.getTitle(),
                                   video.getUsername(),
                                   video.getVideoFileName(),
                                   video.getThumbnailFileName(),
                                   new Meta(video.getDescription(),
                                            GetCategoryCommand.from(categories),
                                            GetTagCommand.from(tags),
                                            GetCommentCommand.from(comments)));
    }
}
