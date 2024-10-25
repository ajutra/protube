package com.tecnocampus.LS2.protube_back.domain.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tecnocampus.LS2.protube_back.domain.model.User;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.domain.processor.deserializer.StoreVideoCommandDeserializer;
import com.tecnocampus.LS2.protube_back.domain.service.StoreUserService;
import com.tecnocampus.LS2.protube_back.domain.service.StoreVideoService;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreUserCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreVideoCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class VideoProcessor {
    private final StoreVideoService storeVideoService;
    private final StoreUserService storeUserService;

    private List<StoreVideoCommand> readJsonFiles(String folderPath) throws IOException {
        List<StoreVideoCommand> commands = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(
                new SimpleModule()
                        .addDeserializer(StoreVideoCommand.class, new StoreVideoCommandDeserializer())
        );

        try (Stream<Path> pathStream = Files.list(Paths.get(folderPath))) {
                pathStream.filter(path -> path.toString().endsWith(".json"))
                        .forEach(path -> {
                            try {
                                StoreVideoCommand command = objectMapper.readValue(new File(path.toString()), StoreVideoCommand.class);
                                commands.add(command);
                            } catch (IOException e) {
                                System.err.println("\nError reading file: " + path);
                            }
                        });
        }

        return commands;
    }

    private User storeUserIfNotExists(StoreVideoCommand command) {
        StoreUserCommand storeUserCommand = StoreUserCommand.from(command.username());
        User user = User.from(storeUserCommand);

        try {
            storeUserService.storeUser(StoreUserCommand.from(command.username()));
        } catch (IllegalArgumentException ignored) {
            // User already exists, we can continue
        }

        return user;
    }

    private boolean videoExists(StoreVideoCommand command, User user) {
        Video video = Video.from(command, user);

        try {
            storeVideoService.checkIfVideoAlreadyExists(video);
        } catch (IllegalArgumentException ignored) {
            return true;
        }

        return false;
    }

    private void processVideos(String folderPath) throws IOException {
        System.out.println("\nProcessing videos...");
        List<StoreVideoCommand> commands = readJsonFiles(folderPath);

        for (StoreVideoCommand command : commands) {
            System.out.println("\nProcessing video: " + command.videoFileName() + "...");
            User user = storeUserIfNotExists(command);

            if (!videoExists(command, user)) {
                System.out.println("Storing video: " + command.videoFileName() + "...");
                storeVideoService.storeVideo(command);
                System.out.println("Video stored successfully!");

            } else {
                System.out.println("Video already exists!, skipping...");
            }
        }

        System.out.println("\nAll videos processed!");
    }

    public void processVideosFrom(String folderPath) {
        try {
            processVideos(folderPath);
        } catch (IOException e) {
            System.err.println("\nError reading files from folder: " + e.getMessage());
        }
    }
}