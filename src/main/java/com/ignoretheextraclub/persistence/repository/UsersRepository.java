package com.ignoretheextraclub.persistence.repository;

import com.ignoretheextraclub.model.data.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

/**
 * Created by caspar on 05/03/17.
 */
public interface UsersRepository extends MongoRepository<User, String>
{
    @Query("{ 'username' : ?0 }")
    Optional<User> findUserByUsername(String username);
}
