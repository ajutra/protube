package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Category;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.domain.model.User;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.StoreVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.StoreVideoUseCase;
import com.tecnocampus.LS2.protube_back.port.out.StoreCategoryPort;
import com.tecnocampus.LS2.protube_back.port.out.StoreTagPort;
import com.tecnocampus.LS2.protube_back.port.out.StoreVideoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreVideoService implements StoreVideoUseCase {
    private final StoreVideoPort storeVideoPort;
    private final StoreTagPort storeTagPort;
    private final StoreCategoryPort storeCategoryPort;

    @Override
    public void storeVideo(StoreVideoCommand storeVideoCommand) {
        // TODO: Check if user exists (Get User needs to be implemented first)
        User user = new User("username");
        //throw new NoSuchElementException("User not found");

        // TODO: Check if video exists (same title and user, Get User needs to be implemented first)
        //throw new IllegalArgumentException("Video already exists");

        // Store video
        Set<Tag> tags = checkAndStore(storeVideoCommand.tags(), Tag::from, storeTagPort::storeTag);
        Set<Category> categories = checkAndStore(storeVideoCommand.categories(), Category::from, storeCategoryPort::storeCategory);
        Video video = createVideo(storeVideoCommand, user);

        storeVideoPort.storeVideo(video, tags, categories, Set.of());

        //TODO: Implement comments (Get/Post User needs to be implemented first)
    }

    private Video createVideo(StoreVideoCommand storeVideoCommand, User user) {
        Video video = new Video();
        video.setWidth(storeVideoCommand.width());
        video.setHeight(storeVideoCommand.height());
        video.setDuration(storeVideoCommand.duration());
        video.setTitle(storeVideoCommand.title());

        if (storeVideoCommand.description() != null)
            video.setDescription(storeVideoCommand.description());
        else
            video.setDescription("");

        video.setUsername(user.username());

        if (storeVideoCommand.videoFileName() != null)
            video.setVideoFileName(storeVideoCommand.videoFileName());
        else
            video.setVideoFileName(storeVideoCommand.title() + user.username() + ".mp4");

        if (storeVideoCommand.thumbnailFileName() != null)
            video.setThumbnailFileName(storeVideoCommand.thumbnailFileName());
        else
            video.setThumbnailFileName(storeVideoCommand.title() + user.username() + ".webp");

        return video;
    }

    private <T> Set<T> checkAndStore(List<String> names, Function<String, T> mapper, Consumer<T> store) {
        if (names == null)
            return Set.of();

        Set<T> result = names.stream().map(mapper).collect(Collectors.toSet());
        result.forEach(store);

        return result;
    }
}
