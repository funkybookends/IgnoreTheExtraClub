package com.ignoretheextraclub.persistence.repository;

import com.ignoretheextraclub.model.data.Pattern;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

import static com.ignoretheextraclub.model.data.Pattern.NAMES_FIELD;

/**
 * Created by caspar on 04/03/17.
 */
public interface PatternRepository extends MongoRepository<Pattern, String>
{
    @Query("{ '" + NAMES_FIELD + ".name' : ?0 }")
    Optional<Pattern> findByName(String name);
}
