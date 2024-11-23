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

import java.io.IOException;
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
    public ResponseEntity<Void> storeVideo(@Valid @RequestBody StoreVideoCommand storeVideoCommand) {
        storeVideoUseCase.storeVideo(storeVideoCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/videos/{id}")
    public GetVideoCommand getVideoById(@PathVariable @Valid @NotBlank String id) {
        return getVideoByIdUseCase.getVideoById(id);
    }
    @PostMapping("/videos/upload")
    public ResponseEntity<Void> uploadVideo(@RequestParam("videoFile") MultipartFile videoFile,
                                            @RequestParam("thumbnailFile") MultipartFile thumbnailFile,
                                            @RequestParam("title") String title,
                                            @RequestParam("description") String description,
                                            @RequestParam("username") String username) throws IOException {
        storeVideoUseCase.storeVideo(videoFile, thumbnailFile, title, description, username);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
