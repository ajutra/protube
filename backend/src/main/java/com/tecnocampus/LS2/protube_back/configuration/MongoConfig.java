package com.tecnocampus.LS2.protube_back.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.tecnocampus.LS2.protube_back.adapter.out.persistence.mongo.repository")
public class MongoConfig {
}
