package com.ignoretheextraclub.configuration;

import com.ignoretheextraclub.model.Pattern;
import com.ignoretheextraclub.persistence.Patterns;
import com.ignoretheextraclub.persistence.impl.MongoPatternRepository;
import com.ignoretheextraclub.properties.MongoProperties;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by caspar on 02/03/17.
 */
@Configuration
public class MongoConfiguration
{
    @Bean
    @ConfigurationProperties(prefix = "mongo")
    public MongoProperties mongoProperties()
    {
        return new MongoProperties();
    }

    @Bean
    public MongoClient mongoClient(final MongoProperties mongoProperties)
    {
        return new MongoClient(mongoProperties.getHost(), mongoProperties.getPort());
    }

    @Bean
    public MongoDatabase mongoDatabase(final MongoClient mongoClient, final MongoProperties mongoProperties)
    {
        return mongoClient.getDatabase(mongoProperties.getDbName());
    }

    @Bean
    public MongoCollection<Document> patternCollection(final MongoDatabase mongoDatabase)
    {
        return mongoDatabase.getCollection(Pattern.COLLECTION_NAME);
    }

    @Bean
    public Patterns patternRepository(final MongoCollection<Document> patternMongoCollection)
    {
        return new MongoPatternRepository(patternMongoCollection);
    }
}
