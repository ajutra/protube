package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.port.in.command.GetCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCommentCommand;
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
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentRestController {

    private final StoreCommentUseCase storeCommentUseCase;

    private final GetCommentsByUsernameUseCase getCommentsByUsernameUseCase;

    @PostMapping
    public ResponseEntity<Void> storeComment(@RequestBody StoreCommentCommand storeCommentCommand) {
            storeCommentUseCase.storeComment(storeCommentCommand);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/user/{username}")
    public List<GetCommentCommand> getCommentsByUsername(@Valid @PathVariable @NotBlank String username) {
        return  getCommentsByUsernameUseCase.getCommentsByUsername(username);

    }
}
