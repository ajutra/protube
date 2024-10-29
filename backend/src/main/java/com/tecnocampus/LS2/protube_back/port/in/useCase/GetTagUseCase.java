package com.tecnocampus.LS2.protube_back.port.in.useCase;

import com.tecnocampus.LS2.protube_back.port.in.command.GetTagCommand;

public interface GetTagUseCase {
    GetTagCommand getTag(String tagName);
}
