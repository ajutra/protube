package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.port.in.command.GetTagCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllTagsUseCase;
import com.tecnocampus.LS2.protube_back.port.out.GetTagPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllTagsService implements GetAllTagsUseCase {
    private final GetTagPort getTagPort;


    @Override
    public List<GetTagCommand> getAllTags() {
        return getTagPort.getAllTags().stream()
                .map(GetTagCommand::from)
                .toList();
    }
}
