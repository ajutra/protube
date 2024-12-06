package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.domain.model.Field;
import com.tecnocampus.LS2.protube_back.domain.model.PlayerPageVideo;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.in.command.GetVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.SearchVideoResultCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetVideoByIdUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.SearchVideosUseCase;
import com.tecnocampus.LS2.protube_back.port.out.GetVideoPort;
import com.tecnocampus.LS2.protube_back.port.out.SearchVideoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GetVideoService implements GetVideoByIdUseCase, SearchVideosUseCase {
    private final GetVideoPort getVideoPort;
    private final SearchVideoPort searchVideoPort;

    Video getVideoByTitleAndUsername(String title, String username) {
        return getVideoPort.getVideoByTitleAndUsername(title, username);
    }

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

    @Override
    public List<SearchVideoResultCommand> searchVideos(String text) {
        return searchVideoPort.searchVideos(text).stream()
                .map(SearchVideoResultCommand::from)
                .toList();
    }
}
