package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.domain.model.User;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCategoryCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreTagCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreVideoUseCase;
import com.tecnocampus.LS2.protube_back.port.out.StoreVideoPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StoreVideoService implements StoreVideoUseCase {
    private final StoreVideoPort storeVideoPort;
    private final StoreVideoPort searchDbStoreVideoPort;
    private final StoreTagService storeTagService;
    private final StoreCategoryService storeCategoryService;
    private final GetUserService getUserService;
    private final GetVideoService getVideoService;
    private final StoreCommentService storeCommentService;

    public StoreVideoService(
            @Qualifier("postgresStoreVideoPort") StoreVideoPort storeVideoPort,
            @Qualifier("mongoStoreVideoPort") StoreVideoPort searchDbStoreVideoPort,
            StoreTagService storeTagService,
            StoreCategoryService storeCategoryService,
            GetUserService getUserService,
            GetVideoService getVideoService,
            StoreCommentService storeCommentService) {
        this.storeVideoPort = storeVideoPort;
        this.searchDbStoreVideoPort = searchDbStoreVideoPort;
        this.storeTagService = storeTagService;
        this.storeCategoryService = storeCategoryService;
        this.getUserService = getUserService;
        this.getVideoService = getVideoService;
        this.storeCommentService = storeCommentService;
    }

    @Override
    @Transactional
    public void storeVideo(StoreVideoCommand storeVideoCommand) {
        User user = getUserService.getUserByUsername(storeVideoCommand.username());

        Video video = Video.from(storeVideoCommand, user);

        checkIfVideoAlreadyExists(video);

        Set<Tag> tags = processTagCommandsList(storeVideoCommand.tags());
        Set<Category> categories = processCategoryCommandsList(storeVideoCommand.categories());

        video = storeVideoPort.storeAndGetVideo(video, tags, categories);
        searchDbStoreVideoPort.storeVideo(video, tags, categories);

        storeCommentsIfPresent(video, storeVideoCommand);
    }

    private void checkIfVideoAlreadyExists(Video video) {
        try {
            Video databaseVideo = getVideoService.getVideoByTitleAndUsername(
                    video.getTitle(),
                    video.getUsername());

            if (databaseVideo.equals(video))
                throw new IllegalArgumentException("Video already exists");

        } catch (NoSuchElementException ignored) {
            // Video not found, we can continue
        }
    }

    Set<Tag> processTagCommandsList(List<StoreTagCommand> storeTagCommands) {
        if (storeTagCommands == null)
            return Set.of();

        return parseAndStoreCommands(
                storeTagCommands,
                storeTagService::storeAndGetTag,
                Tag::from);
    }

    Set<Category> processCategoryCommandsList(List<StoreCategoryCommand> storeCategoryCommands) {
        if (storeCategoryCommands == null)
            return Set.of();

        return parseAndStoreCommands(
                storeCategoryCommands,
                storeCategoryService::storeAndGetCategory,
                Category::from);
    }

    private <T, R> Set<R> parseAndStoreCommands(
            List<T> commands,
            Function<T, R> storeAndGetFunction,
            Function<T, R> mappingFunction) {

        return commands.stream()
                .map(
                        command -> {
                            try {
                                // We don't want to propagate the exception here,
                                // if the tag/category already exists we just want to use it
                                return storeAndGetFunction.apply(command);
                            } catch (IllegalArgumentException e) {
                                return mappingFunction.apply(command);
                            }
                        }
                )
                .collect(Collectors.toSet());
    }

    private void storeCommentsIfPresent(Video video, StoreVideoCommand storeVideoCommand) {
        if (storeVideoCommand.comments() != null) {
            storeVideoCommand.comments().forEach(
                    storeCommentCommand -> storeCommentService.storeCommentFromStoreVideoService(
                            storeCommentCommand, video)
            );
        }
    }
}
