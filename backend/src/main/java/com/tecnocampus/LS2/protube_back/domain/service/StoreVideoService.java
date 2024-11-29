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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreVideoService implements StoreVideoUseCase {
    private final StoreVideoPort storeVideoPort;
    private final StoreTagService storeTagService;
    private final StoreCategoryService storeCategoryService;
    private final GetUserService getUserService;
    private final GetVideoService getVideoService;
    private final StoreCommentService storeCommentService;

    @Value("${pro_tube.store.dir}")
    private String storageDir;

    @Override
    @Transactional
    public void storeVideo(StoreVideoCommand storeVideoCommand) {
        User user = getUserService.getUserByUsername(storeVideoCommand.username());

        Video video = Video.from(storeVideoCommand, user);

        checkIfVideoAlreadyExists(video);

        Set<Tag> tags = processTagCommandsList(storeVideoCommand.tags());
        Set<Category> categories = processCategoryCommandsList(storeVideoCommand.categories());

        video = storeVideoPort.storeAndGetVideo(video, tags, categories);

        storeCommentsIfPresent(video, storeVideoCommand);
    }

    @Override
    @Transactional
    public void storeVideoWithFiles(MultipartFile file, MultipartFile thumbnail, StoreVideoCommand storeVideoCommand) {
        User user = getUserService.getUserByUsername(storeVideoCommand.username());
        Video video = Video.from(storeVideoCommand, user);
        video.setVideoFileName(file.getOriginalFilename());
        video.setThumbnailFileName(thumbnail.getOriginalFilename());

        Path videoPath = Paths.get(storageDir, video.getVideoFileName());
        Path thumbnailPath = Paths.get(storageDir, video.getThumbnailFileName());

        try {
            // Check if files already exist and change names if necessary
            videoPath = resolveFileNameConflict(videoPath);
            thumbnailPath = resolveFileNameConflict(thumbnailPath);

            // Store video file
            Files.copy(file.getInputStream(), videoPath);

            // Store thumbnail file
            Files.copy(thumbnail.getInputStream(), thumbnailPath);

            // Update video file names with new paths
            video.setVideoFileName(videoPath.getFileName().toString());
            video.setThumbnailFileName(thumbnailPath.getFileName().toString());

            // Store video metadata in the database
            storeVideoPort.storeVideo(video, Set.of(), Set.of());

        } catch (IOException e) {
            // Cleanup files if there's an error
            try {
                Files.deleteIfExists(videoPath);
                Files.deleteIfExists(thumbnailPath);
            } catch (IOException cleanupException) {
                throw new RuntimeException("Error cleaning up files after upload failure", cleanupException);
            }
            throw new RuntimeException("Error uploading files", e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error", e);
        }
    }

    private Path resolveFileNameConflict(Path path) {
        Path resolvedPath = path;
        int counter = 1;
        while (Files.exists(resolvedPath)) {
            String newFileName = String.format("%s_%d%s",
                    stripExtension(path.getFileName().toString()),
                    counter++,
                    getFileExtension(path.getFileName().toString()));
            resolvedPath = path.getParent().resolve(newFileName);
        }
        return resolvedPath;
    }

    private String stripExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex == -1) ? fileName : fileName.substring(0, lastDotIndex);
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex == -1) ? "" : fileName.substring(lastDotIndex);
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
