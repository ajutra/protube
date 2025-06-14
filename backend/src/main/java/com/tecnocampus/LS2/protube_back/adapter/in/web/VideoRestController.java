package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.port.in.command.GetVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.SearchVideoResultCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.EditVideoCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.*;
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
    private final GetAllVideosByUsernameUseCase getAllVideosByUsernameUseCase;
    private final DeleteVideoUseCase deleteVideoUseCase;
    private final EditVideoUseCase editVideoUseCase;
    private final SearchVideosUseCase searchVideosUseCase;

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

    @GetMapping("/videos/search/{text}")
    public List<SearchVideoResultCommand> searchVideos(@PathVariable @Valid @NotBlank String text) {
        return searchVideosUseCase.searchVideos(text);
    }

    @GetMapping("/users/{username}/videos")
    public List<GetVideoCommand> getAllVideosByUsername(@PathVariable @Valid @NotBlank String username) {
        return getAllVideosByUsernameUseCase.getAllVideosByUsername(username);
    }

    @DeleteMapping("/videos/{videoId}")
    public ResponseEntity<Void> deleteVideo(@Valid @NotBlank @PathVariable String videoId) {
        deleteVideoUseCase.deleteVideo(videoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/videos")
    public ResponseEntity<Void> updateVideo(@Valid @RequestBody EditVideoCommand editVideoCommand) {
        editVideoUseCase.editVideo(editVideoCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
