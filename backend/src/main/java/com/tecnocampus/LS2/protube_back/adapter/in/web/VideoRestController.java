package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.port.in.command.GetVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllVideosUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetVideoByIdUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreVideoUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class VideoRestController {
    private final StoreVideoUseCase storeVideoUseCase;
    private final GetAllVideosUseCase getAllVideosUseCase;
    private final GetVideoByIdUseCase getVideoByIdUseCase;

    @GetMapping("/videos")
    public List<GetVideoCommand> getAllVideos() {
        return getAllVideosUseCase.getAllVideos();
    }

    @PostMapping("/videos")
    public ResponseEntity<Void> storeVideo(@RequestPart("file") MultipartFile file,
                                           @RequestPart("thumbnail") MultipartFile thumbnail,
                                           @RequestPart("storeVideoCommand") @Valid StoreVideoCommand storeVideoCommand) {
        storeVideoUseCase.storeVideoWithFiles(file, thumbnail, storeVideoCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/videos/{id}")
    public GetVideoCommand getVideoById(@PathVariable @Valid @NotBlank String id) {
        return getVideoByIdUseCase.getVideoById(id);
    }
}
