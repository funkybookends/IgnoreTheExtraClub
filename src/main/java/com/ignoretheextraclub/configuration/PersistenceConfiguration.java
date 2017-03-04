package com.ignoretheextraclub.configuration;

import com.ignoretheextraclub.persistence.Patterns;
import com.ignoretheextraclub.persistence.impl.MongoPatternRepository;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by caspar on 03/03/17.
 */
@Configuration
public class PersistenceConfiguration
{
    @Autowired
    private MongoCollection<Document> patternCollection;

    @Bean
    public Patterns patterns()
    {
        return new MongoPatternRepository(patternCollection);
    }
}
