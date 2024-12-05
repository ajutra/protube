package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.port.out.UserVideoLikePort;
import com.tecnocampus.LS2.protube_back.port.in.command.GetUserVideoLikeAndDislikeCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserVideoLikeOrDislikeServiceTests {

    @Mock
    private UserVideoLikePort userVideoLikePort;

    @InjectMocks
    private UserVideoLikeOrDislikeService userVideoLikeOrDislikeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void likeVideoCallsPortLikeVideo() {
        String username = "user1";
        String videoId = "video1";

        userVideoLikeOrDislikeService.likeVideo(username, videoId);

        verify(userVideoLikePort).likeVideo(username, videoId);
    }

    @Test
    void dislikeVideoCallsPortDislikeVideo() {
        String username = "user1";
        String videoId = "video1";

        userVideoLikeOrDislikeService.dislikeVideo(username, videoId);

        verify(userVideoLikePort).dislikeVideo(username, videoId);
    }

    @Test
    void removeLikeOrDislikeCallsPortRemoveLikeOrDislike() {
        String username = "user1";
        String videoId = "video1";

        userVideoLikeOrDislikeService.removeLikeOrDislike(username, videoId);

        verify(userVideoLikePort).removeLikeOrDislike(username, videoId);
    }

    @Test
    void getUserVideoLikeAndDislikeReturnsCorrectCommand() {
        String username = "user1";
        String videoId = "video1";
        Map<String, Boolean> likesAndDislikes = Map.of("hasLiked", true, "hasDisliked", false);

        when(userVideoLikePort.getLikesAndDislikes(username, videoId)).thenReturn(likesAndDislikes);

        GetUserVideoLikeAndDislikeCommand result = userVideoLikeOrDislikeService.getUserVideoLikeAndDislike(username, videoId);

        assertTrue(result.hasLiked());
        assertFalse(result.hasDisliked());
    }
}