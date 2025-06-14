package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.port.in.command.GetUserVideoLikeAndDislikeCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreUserCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.VerifyUserCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.EditUserVideoLikeOrDislikeUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetUserVideoLikeAndDislikeUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreUserUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.VerifyUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserRestController {
    private final StoreUserUseCase storeUserUseCase;
    private final GetUserVideoLikeAndDislikeUseCase getUserVideoLikeAndDislikeUseCase;
    private final EditUserVideoLikeOrDislikeUseCase editUserVideoLikeOrDislikeUseCase;
    private final VerifyUserUseCase verifyUserUseCase;

    @PostMapping("/users/register")
    public ResponseEntity<Void> storeUser(@Valid @RequestBody StoreUserCommand storeUserCommand) {
        storeUserUseCase.storeUser(storeUserCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users/{username}/videos/{videoId}/like-status")
    public GetUserVideoLikeAndDislikeCommand getUserVideoLikeAndDislike(
            @Valid @PathVariable String username,
            @Valid @PathVariable String videoId) {
        return getUserVideoLikeAndDislikeUseCase.getUserVideoLikeAndDislike(username, videoId);
    }

    @PostMapping("/users/{username}/videos/{videoId}/like")
    public ResponseEntity<Void> likeVideo(
            @Valid @PathVariable String username,
            @Valid @PathVariable String videoId) {
        editUserVideoLikeOrDislikeUseCase.likeVideo(username, videoId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/users/{username}/videos/{videoId}/dislike")
    public ResponseEntity<Void> dislikeVideo(
            @Valid @PathVariable String username,
            @Valid @PathVariable String videoId) {
        editUserVideoLikeOrDislikeUseCase.dislikeVideo(username, videoId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/users/{username}/videos/{videoId}/likes")
    public ResponseEntity<Void> removeLikeOrDislike(
            @Valid @PathVariable String username,
            @Valid @PathVariable String videoId) {
        editUserVideoLikeOrDislikeUseCase.removeLikeOrDislike(username, videoId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/users/login")
    public ResponseEntity<Void> verifyUserAuthCredentials(@Valid @RequestBody VerifyUserCommand verifyUserCommand) {
        verifyUserUseCase.verifyUser(verifyUserCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
