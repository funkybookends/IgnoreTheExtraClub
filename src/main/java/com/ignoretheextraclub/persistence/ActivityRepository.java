package com.ignoretheextraclub.persistence;

import com.ignoretheextraclub.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by caspar on 02/04/17.
 */
public interface ActivityRepository extends MongoRepository<Activity, String>
{
}
