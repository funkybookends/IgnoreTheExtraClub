package com.ignoretheextraclub.service.post;

import com.ignoretheextraclub.model.data.Post;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by caspar on 02/04/17.
 */
@Service
public interface PostService
{
    Optional<Post> getPostByTitle(String title);

    Optional<Post> getPostById(String id);

    Post save(Post newPost);

    Page newest(int page);

    Page newest(int page, int size);
}
