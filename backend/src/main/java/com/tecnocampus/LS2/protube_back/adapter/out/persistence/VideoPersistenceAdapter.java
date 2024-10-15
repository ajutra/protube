package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mapper.VideoMapper;
import com.tecnocampus.LS2.protube_back.domain.model.Video;
import com.tecnocampus.LS2.protube_back.port.out.GetVideosPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VideoPersistenceAdapter implements GetVideosPort {
    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;

    public List<Video> getAllVideos() {
        return videoRepository.findAll().stream()
                .map(videoMapper::toDomain)
                .collect(Collectors.toList());
    }
}
