package com.tecnocampus.LS2.protube_back.port.in.useCase;

import com.tecnocampus.LS2.protube_back.port.in.command.SearchVideoResultCommand;

import java.util.List;

public interface SearchVideosUseCase {
    List<SearchVideoResultCommand> searchVideos(String text);
}
