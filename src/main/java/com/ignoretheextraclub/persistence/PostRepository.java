package com.ignoretheextraclub.persistence;

import com.ignoretheextraclub.model.data.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Created by caspar on 02/04/17.
 */
public interface PostRepository extends MongoRepository<Post, String>
{
    Optional<Post> findByTitle(String title);
}
