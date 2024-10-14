package com.tecnocampus.LS2.protube_back.domain.model;

public record Comment (
        String id,
        String video_id,
        String user_id,
        String text
){
}
