package com.ignoretheextraclub.persistence;

import com.ignoretheextraclub.model.data.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Created by caspar on 02/04/17.
 */
public interface ActivityRepository extends MongoRepository<Activity, String>
{
}
