package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.port.in.command.EditCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.GetCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.EditCommentUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.DeleteCommentUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllCommentsByVideoUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetCommentsByUsernameUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreCommentUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentRestController {
    private final StoreCommentUseCase storeCommentUseCase;
    private final GetAllCommentsByVideoUseCase getAllCommentsByVideoUseCase;
    private final GetCommentsByUsernameUseCase getCommentsByUsernameUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;
    private final EditCommentUseCase editCommentUseCase;

    @PostMapping("/comments")
    public ResponseEntity<Void> storeComment(@RequestBody StoreCommentCommand storeCommentCommand) {
            storeCommentUseCase.storeComment(storeCommentCommand);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/videos/{videoId}/comments")
    public List<GetCommentCommand> getCommentsByVideoId(@Valid @NotBlank @PathVariable String videoId) {
        return getAllCommentsByVideoUseCase.getAllCommentsByVideoId(videoId);
    }

    @GetMapping("/users/{username}/comments")
    public List<GetCommentCommand> getCommentsByUsername(@Valid @PathVariable @NotBlank String username) {
        return  getCommentsByUsernameUseCase.getCommentsByUsername(username);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@Valid @NotBlank @PathVariable String commentId) {
        deleteCommentUseCase.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/comments")
    public ResponseEntity<Void> editComment(@Valid @RequestBody EditCommentCommand editCommentCommand) {
        editCommentUseCase.editComment(editCommentCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
