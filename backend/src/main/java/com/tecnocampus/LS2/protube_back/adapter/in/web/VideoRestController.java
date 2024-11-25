package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.port.in.command.GetVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.DeleteVideoUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllVideosUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetVideoByIdUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreVideoUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class VideoRestController {
    private final StoreVideoUseCase storeVideoUseCase;
    private final GetAllVideosUseCase getAllVideosUseCase;
    private final GetVideoByIdUseCase getVideoByIdUseCase;
    private final DeleteVideoUseCase deleteVideoUseCase;

    @GetMapping("/videos")
    public List<GetVideoCommand> getAllVideos() {
        return getAllVideosUseCase.getAllVideos();
    }

    @PostMapping("/videos")
    public ResponseEntity<Void> storeVideo(@Valid @RequestBody StoreVideoCommand storeVideoCommand) {
        storeVideoUseCase.storeVideo(storeVideoCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/videos/{id}")
    public GetVideoCommand getVideoById(@PathVariable @Valid @NotBlank String id) {
        return getVideoByIdUseCase.getVideoById(id);
    }

    @DeleteMapping("/videos/{videoId}")
    public ResponseEntity<Void> deleteVideo(@Valid @NotBlank @PathVariable String videoId) {
        deleteVideoUseCase.deleteVideo(videoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
