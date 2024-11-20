package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Comment;
import com.tecnocampus.LS2.protube_back.port.in.command.GetCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetCommentsByUsernameUseCase;
import com.tecnocampus.LS2.protube_back.port.out.GetCommentPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCommentsByUsernameService implements GetCommentsByUsernameUseCase {
    private final GetCommentPort getCommentPort;

    @Override
    public List<GetCommentCommand> getCommentsByUsername(String username) {
        List<Comment> comments = getCommentPort.getCommentsByUsername(username);

        return comments.stream()
                .map(GetCommentCommand::from)
                .toList();
    }
}
