package com.example.springaidemo.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = {
                "com.example.springaidemo"
        },
        mongoTemplateRef = "primaryMongoTemplate")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.primary.uri}")
    private String mongoURI;

    @Value("${spring.data.mongodb.primary.database}")
    private String mongoDatabase;

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(mongoURI);
    }

    @Override
    protected String getDatabaseName() {
        return mongoDatabase;
    }

    @Bean(name = "primaryMongoTemplate")
    public MongoTemplate primaryMongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }
}

