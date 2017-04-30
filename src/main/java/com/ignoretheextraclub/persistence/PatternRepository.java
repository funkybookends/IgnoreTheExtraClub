package com.ignoretheextraclub.persistence;

import com.ignoretheextraclub.model.juggling.Pattern;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

import static com.ignoretheextraclub.model.juggling.Pattern.NAMES_FIELD;

/**
 * Created by caspar on 04/03/17.
 */
public interface PatternRepository extends MongoRepository<Pattern, String>
{
    @Query("{ '" + NAMES_FIELD + ".name' : ?0 }")
    Optional<Pattern> findByName(String name);

    Optional<Pattern> findByComments_Id(String comment);

    List<Pattern> findDistinctPatternsByComments_Username(String username);
}
