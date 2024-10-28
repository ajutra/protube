package com.tecnocampus.LS2.protube_back.port.in.useCase;


import com.tecnocampus.LS2.protube_back.port.in.command.GetTagCommand;

import java.util.List;

public interface GetAllTagsUseCase {
    List<GetTagCommand> getAllTags();


}
