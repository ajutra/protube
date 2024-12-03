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
    @TextIndexed
    private String title;
    private String username;
    private Set<String> tags;
    private Set<String> categories;
}
