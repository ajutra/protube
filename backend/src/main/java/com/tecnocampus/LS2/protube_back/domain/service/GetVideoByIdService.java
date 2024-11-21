package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Field;
import com.tecnocampus.LS2.protube_back.domain.model.PlayerPageVideo;
import com.tecnocampus.LS2.protube_back.port.in.command.GetVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetVideoByIdUseCase;
import com.tecnocampus.LS2.protube_back.port.out.GetVideoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class GetVideoByIdService implements GetVideoByIdUseCase {
    private final GetVideoPort getVideoPort;

    @Override
    public GetVideoCommand getVideoById(String id) {
        Set<Field> fields = Set.of(Field.CATEGORIES, Field.TAGS, Field.COMMENTS);

        PlayerPageVideo playerPageVideo = getVideoPort.getVideoWithFieldsById(id, fields);
        return GetVideoCommand.from(
                playerPageVideo.video(),
                playerPageVideo.categories(),
                playerPageVideo.tags(),
                playerPageVideo.comments()
        );
    }
}

