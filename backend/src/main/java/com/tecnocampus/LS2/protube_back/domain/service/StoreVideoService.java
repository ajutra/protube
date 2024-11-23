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
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    private final String VIDEO_UPLOAD_DIR = "/home/laura/protube/store/";
    private final String THUMBNAIL_UPLOAD_DIR = "/home/laura/protube/store/";

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
    public void storeVideo(MultipartFile videoFile, MultipartFile thumbnailFile, String title, String description, String username) throws IOException {
        try {
            File videoDest = new File(VIDEO_UPLOAD_DIR + videoFile.getOriginalFilename());
            videoFile.transferTo(videoDest);

            File thumbnailDest = new File(THUMBNAIL_UPLOAD_DIR + thumbnailFile.getOriginalFilename());
            thumbnailFile.transferTo(thumbnailDest);

            FFprobe ffprobe = new FFprobe("/usr/bin/ffprobe");
            FFmpegProbeResult probeResult = ffprobe.probe(videoDest.getAbsolutePath());
            FFmpegStream stream = probeResult.getStreams().get(0);

            Video video = new Video();
            video.setVideoFileName(videoFile.getOriginalFilename());
            video.setThumbnailFileName(thumbnailFile.getOriginalFilename());
            video.setTitle(title);
            video.setDescription(description);
            video.setUsername(username);
            video.setDuration((int) stream.duration);
            video.setHeight(stream.height);
            video.setWidth(stream.width);

            storeVideoPort.storeVideo(video, Set.of(), Set.of());
        } catch (Exception e) {
            throw new IOException("Error saving files", e);
        }
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
