package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.command.GetCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllCommentsByVideoUseCase;
import com.tecnocampus.LS2.protube_back.port.out.GetCommentPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCommentsByVideoService implements GetAllCommentsByVideoUseCase {
    private final GetCommentPort getCommentPort;

    @Override
    public List<GetCommentCommand> getAllCommentsByVideo(String videoId) {
        return getCommentPort.getAllCommentsByVideo(videoId).stream()
                .map(GetCommentCommand::from)
                .toList();
    }
}
