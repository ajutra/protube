package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.jpaEntity.VideoJpaEntity;
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
        VideoJpaEntity videoJpaEntity = new VideoJpaEntity();
        videoJpaEntity.setVideo_id(videoId);

        return getCommentPort.getAllCommentsByVideo(videoJpaEntity).stream()
                .map(GetCommentCommand::from)
                .toList();
    }
}
