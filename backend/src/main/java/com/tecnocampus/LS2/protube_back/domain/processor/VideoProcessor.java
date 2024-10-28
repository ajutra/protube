package com.tecnocampus.LS2.protube_back.domain.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
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

    List<StoreVideoCommand> readJsonFiles(String folderPath) throws IOException {
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
                            }});
        }

        return commands;
    }

    void storeUserIfNotExists(StoreVideoCommand command) {
        try {
            storeUserService.storeUser(StoreUserCommand.from(command.username()));
        } catch (IllegalArgumentException ignored) {
            // User already exists, we can continue
        }
    }

    private void processVideos(String folderPath) throws IOException {
        System.out.println("\nProcessing videos...");
        List<StoreVideoCommand> commands = readJsonFiles(folderPath);

        for (StoreVideoCommand command : commands) {
            System.out.println("\nProcessing video: " + command.videoFileName() + "...");
            storeUserIfNotExists(command);

            try {
                System.out.println("Storing video: " + command.videoFileName() + "...");
                storeVideoService.storeVideo(command);
                System.out.println("Video stored successfully!");

            } catch (IllegalArgumentException e) {
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