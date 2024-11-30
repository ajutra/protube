package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCategoryCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreTagCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.UpdateVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.EditVideoUseCase;
import com.tecnocampus.LS2.protube_back.port.out.EditVideoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EditVideoService implements EditVideoUseCase {
    private final StoreTagService storeTagService;
    private final StoreCategoryService storeCategoryService;
    private final GetVideoService getVideoService;
    private final EditVideoPort editVideoPort;

    @Override @Transactional
    public void editVideo(UpdateVideoCommand updateVideoCommand, String videoId) {
        Video video = getVideoService.getVideoById(videoId);
        if (!video.getId().equals(updateVideoCommand.id())) {
            throw new IllegalArgumentException("Video ID mismatch.");
        }
        video.setTitle(updateVideoCommand.title());
        video.setDescription(updateVideoCommand.description());
        editVideoPort.editVideo(video);
    }

    Set<Tag> processTagCommandsList(List<StoreTagCommand> storeTagCommands) {
        if (storeTagCommands == null)
            return Set.of();

        return parseAndStoreCommands(storeTagCommands, storeTagService::storeAndGetTag, Tag::from);
    }

    Set<Category> processCategoryCommandsList(List<StoreCategoryCommand> storeCategoryCommands) {
        if (storeCategoryCommands == null)
            return Set.of();

        return parseAndStoreCommands(storeCategoryCommands, storeCategoryService::storeAndGetCategory, Category::from);
    }

    private <T, R> Set<R> parseAndStoreCommands(List<T> commands, Function<T, R> storeAndGetFunction, Function<T, R> mappingFunction) {
        return commands.stream()
                .map(command -> {
                    try {
                        // We don't want to propagate the exception here,
                        // if the tag/category already exists we just want to use it
                        return storeAndGetFunction.apply(command);
                    } catch (IllegalArgumentException e) {
                        return mappingFunction.apply(command);
                    }
                })
                .collect(Collectors.toSet());
    }
}
