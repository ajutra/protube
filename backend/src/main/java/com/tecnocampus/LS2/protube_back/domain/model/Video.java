package com.tecnocampus.LS2.protube_back.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Video {
        String id;
        int width;
        int height;
        int duration;
        String title;
        String description;
        String username;
        String videoFileName;
        String thumbnailFileName;
}
