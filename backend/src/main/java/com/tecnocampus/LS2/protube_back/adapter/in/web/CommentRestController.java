package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.port.in.command.GetCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllCommentsByVideoUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreCommentUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentRestController {

    private final StoreCommentUseCase storeCommentUseCase;
    private final GetAllCommentsByVideoUseCase getAllCommentsByVideoUseCase;

    @PostMapping("/comments")
    public ResponseEntity<Void> storeComment(@RequestBody StoreCommentCommand storeCommentCommand) {
            storeCommentUseCase.storeComment(storeCommentCommand);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/videos/comments/{videoId}")
    public List<GetCommentCommand> getCommentsByVideoId(@Valid @NotBlank @PathVariable String videoId) {
        return getAllCommentsByVideoUseCase.getAllCommentsByVideo(videoId);
    }
}
