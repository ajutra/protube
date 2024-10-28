package com.tecnocampus.LS2.protube_back.domain.service;

import com.tecnocampus.LS2.protube_back.port.in.command.GetTagCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetTagUseCase;
import com.tecnocampus.LS2.protube_back.port.out.GetTagPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTagService implements GetTagUseCase {
    private final GetTagPort getTagPort;

    @Override
    public GetTagCommand getTag(String tagName) {
        return GetTagCommand.from(getTagPort.getTag(tagName));
    }
}
