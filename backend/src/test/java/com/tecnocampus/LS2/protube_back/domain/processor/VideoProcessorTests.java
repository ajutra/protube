package com.tecnocampus.LS2.protube_back.domain.processor;

import com.tecnocampus.LS2.protube_back.domain.service.StoreUserService;
import com.tecnocampus.LS2.protube_back.domain.service.StoreVideoService;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreUserCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreVideoCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class VideoProcessorTests {

    @Autowired
    private Environment env;

    private String VALID_FOLDER_PATH;

    @Mock
    private StoreVideoService storeVideoService;

    @Mock
    private StoreUserService storeUserService;

    @InjectMocks
    private VideoProcessor videoProcessor;

    @BeforeEach
    void setUp() {
        VALID_FOLDER_PATH = env.getProperty("pro_tube.store.dir");
    }

    @Test
    void readJsonFiles_validFolderPath_returnsListOfCommands() throws IOException {
        List<StoreVideoCommand> commands = videoProcessor.readJsonFiles(VALID_FOLDER_PATH);
        assertFalse(commands.isEmpty());
    }

    @Test
    void readJsonFiles_invalidFolderPath_throwsIOException() {
        assertThrows(IOException.class, () -> videoProcessor.readJsonFiles("invalid/path"));
    }

    @Test
    void processVideosFrom_validFolderPath_processesVideos() {
        videoProcessor.processVideosFrom(VALID_FOLDER_PATH);
        verify(storeVideoService, atLeastOnce()).storeVideo(any(StoreVideoCommand.class));
    }

    @Test
    void processVideosFrom_validFolderPath_doesNotStoreVideoIfVideoAlreadyExists() {
        // Redirect System.out to a ByteArrayOutputStream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        doThrow(IllegalArgumentException.class).when(storeVideoService).storeVideo(any(StoreVideoCommand.class));
        videoProcessor.processVideosFrom(VALID_FOLDER_PATH);
        verify(storeVideoService, atLeastOnce()).storeVideo(any(StoreVideoCommand.class));

        String expectedMessage = "Video already exists!, skipping...";
        assertTrue(outContent.toString().contains(expectedMessage));

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    void processVideosFrom_invalidFolderPath_logsError() {
        videoProcessor.processVideosFrom("invalid/path");
        verify(storeVideoService, never()).storeVideo(any(StoreVideoCommand.class));
    }

    @Test
    void storeUserIfNotExists_userDoesNotExist_storesUser() {
        StoreVideoCommand command = mock(StoreVideoCommand.class);
        when(command.username()).thenReturn("newUser");
        videoProcessor.storeUserIfNotExists(command);
        verify(storeUserService).storeUser(any(StoreUserCommand.class));
    }

    @Test
    void storeUserIfNotExists_userAlreadyExists_doesNotStoreUser() {
        StoreVideoCommand command = mock(StoreVideoCommand.class);
        when(command.username()).thenReturn("existingUser");
        doThrow(IllegalArgumentException.class).when(storeUserService).storeUser(any(StoreUserCommand.class));
        videoProcessor.storeUserIfNotExists(command);
        verify(storeUserService).storeUser(any(StoreUserCommand.class));
    }
}