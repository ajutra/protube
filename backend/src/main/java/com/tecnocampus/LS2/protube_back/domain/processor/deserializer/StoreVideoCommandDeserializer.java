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

        int width = getIntValue(node, "width");
        int height = getIntValue(node, "height");
        int duration = getIntValue(node, "duration");
        String title = getTextValue(node, "title");
        String description = "";
        String username = getTextValue(node, "user");
        String videoId = getIdValue(node);
        String videoFileName = videoId + ".mp4";
        String thumbnailFileName = videoId + ".webp";
        Set<StoreTagCommand> tags = new HashSet<>();
        Set<StoreCategoryCommand> categories = new HashSet<>();
        List<StoreCommentCommand> comments = new ArrayList<>();

        if (node.has("meta")) {
            JsonNode metaNode = node.get("meta");

            description = metaNode.has("description") ?
                    getTextValue(metaNode, "description") :
                    "";

            if (metaNode.has("tags"))
                metaNode.get("tags").forEach(tagNode ->
                        tags.add(
                                StoreTagCommand.from(getTextValue(tagNode))
                        )
                );

            if (metaNode.has("categories"))
                metaNode.get("categories").forEach(categoryNode ->
                        categories.add(
                                StoreCategoryCommand.from(getTextValue(categoryNode))
                        )
                );

            if (metaNode.has("comments"))
                metaNode.get("comments").forEach(commentNode -> {
                    String text = getTextValue(commentNode, "text");
                    String author = getTextValue(commentNode, "author");
                    comments.add(StoreCommentCommand.from(author, text));
                });
        }

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

    private int getIntValue(JsonNode node, String fieldName) {
        if (!node.has(fieldName) || !node.get(fieldName).isNumber()) {
            throw new IllegalArgumentException("Invalid or missing value for field: " + fieldName);
        }
        return node.get(fieldName).asInt();
    }

    private String getTextValue(JsonNode node, String fieldName) {
        if (!node.has(fieldName) || !node.get(fieldName).isTextual()) {
            throw new IllegalArgumentException("Invalid or missing value for field: " + fieldName);
        }
        return node.get(fieldName).asText();
    }

    private String getIdValue(JsonNode node) {
        if (!node.has("id") || !node.get("id").isNumber()) {
            throw new IllegalArgumentException("Invalid or missing value for field: id");
        }
        return node.get("id").asText();
    }

    private String getTextValue(JsonNode node) {
        if (!node.isTextual()) {
            throw new IllegalArgumentException("Invalid text value");
        }
        return node.asText();
    }
}