package com.tecnocampus.LS2.protube_back.configuration;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo.document.VideoDocument;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo.repository")
public class MongoConfig {
    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void initIndexes() {
        IndexOperations indexOps = mongoTemplate.indexOps(VideoDocument.class);
        TextIndexDefinition textIndex = new TextIndexDefinition.TextIndexDefinitionBuilder()
                .onField("title", 1000F)
                .onField("username", 1000F)
                .onField("tags")
                .onField("categories", 10F)
                .build();

        indexOps.ensureIndex(textIndex);
    }
}
