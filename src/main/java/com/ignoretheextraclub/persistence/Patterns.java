package com.ignoretheextraclub.persistence;

import com.ignoretheextraclub.model.Pattern;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

import static com.ignoretheextraclub.model.Pattern.NAMES_FIELD;

/**
 * Created by caspar on 04/03/17.
 */
public interface Patterns extends MongoRepository<Pattern, String>
{
    @Query("{ '" + NAMES_FIELD + ".name' : ?0 }")
    Pattern findByName(String name);
}
