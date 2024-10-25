package com.tecnocampus.LS2.protube_back.domain.processor.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCategoryCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreCommentCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreTagCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreVideoCommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StoreVideoCommandDeserializer extends JsonDeserializer<StoreVideoCommand> {

    @Override
    public StoreVideoCommand deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        int width = node.get("width").asInt();
        int height = node.get("height").asInt();
        int duration = node.get("duration").asInt();
        String title = node.get("title").asText();
        String username = node.get("user").asText();
        String videoId = node.get("id").asText();

        JsonNode metaNode = node.get("meta");
        String description = metaNode.get("description").asText();
        Set<StoreTagCommand> tags = new HashSet<>();
        metaNode.get("tags").forEach(tagNode -> tags.add(StoreTagCommand.from(tagNode.asText())));
        Set<StoreCategoryCommand> categories = new HashSet<>();
        metaNode.get("categories").forEach(categoryNode -> categories.add(StoreCategoryCommand.from(categoryNode.asText())));

        List<StoreCommentCommand> comments = new ArrayList<>();
        metaNode.get("comments").forEach(commentNode -> {
            String text = commentNode.get("text").asText();
            String author = commentNode.get("author").asText();
            comments.add(StoreCommentCommand.from(author, text));
        });

        String videoFileName = videoId + ".mp4";
        String thumbnailFileName = videoId + ".webp";

        return new StoreVideoCommand(
                width,
                height,
                duration,
                title,
                description,
                username,
                videoFileName,
                thumbnailFileName,
                tags.stream().toList(),
                categories.stream().toList(),
                comments);
    }
}