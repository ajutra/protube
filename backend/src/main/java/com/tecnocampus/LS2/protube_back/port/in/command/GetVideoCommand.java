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
        int likes,
        int dislikes,
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
        List<GetCategoryCommand> categoriesCommand =
                categories.stream()
                        .map(GetCategoryCommand::from)
                        .toList();
        List<GetTagCommand> tagsCommand =
                tags.stream()
                        .map(GetTagCommand::from)
                        .toList();
        List<GetCommentCommand> commentCommand =
                comments.stream()
                        .map(GetCommentCommand::from)
                        .toList();
        return new GetVideoCommand(video.getId(),
                                   video.getWidth(),
                                   video.getHeight(),
                                   video.getDuration(),
                                   video.getTitle(),
                                   video.getUsername(),
                                   video.getVideoFileName(),
                                   video.getThumbnailFileName(),
                                   video.getLikes(),
                                   video.getDislikes(),
                                   new Meta(video.getDescription(),
                                            categoriesCommand,
                                            tagsCommand,
                                            commentCommand));
    }
}
