package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.port.in.command.GetVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllVideosUseCase;
import com.tecnocampus.LS2.protube_back.port.out.GetVideoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllVideosService implements GetAllVideosUseCase {
    private final GetVideoPort getVideoPort;

    @Override
    public List<GetVideoCommand> getAllVideos() {
        return getVideoPort.getAllVideosWithTagsCategoriesAndComments().stream()
                .map(data ->
                    GetVideoCommand.from(data.video(),
                            data.categories(),
                            data.tags(),
                            data.comments()))
                .toList();
    }
}
