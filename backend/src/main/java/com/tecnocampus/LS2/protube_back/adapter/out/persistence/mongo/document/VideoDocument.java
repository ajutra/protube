package com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo.document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "video")
@AllArgsConstructor
@Getter
public class VideoDocument {
    @Id
    private String id;

    @TextIndexed(weight = 1000)
    private String title;

    @TextIndexed(weight = 1000)
    private String username;

    @TextIndexed
    private Set<String> tags;

    @TextIndexed(weight = 10)
    private Set<String> categories;
}
