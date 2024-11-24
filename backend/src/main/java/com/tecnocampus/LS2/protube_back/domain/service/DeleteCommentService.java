package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.port.in.useCase.DeleteCommentUseCase;
import com.tecnocampus.LS2.protube_back.port.out.DeleteCommentPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCommentService implements DeleteCommentUseCase {
    private final DeleteCommentPort deleteCommentPort;

    @Override
    public void deleteComment(String commentId) {
        deleteCommentPort.deleteComment(commentId);
    }
}
