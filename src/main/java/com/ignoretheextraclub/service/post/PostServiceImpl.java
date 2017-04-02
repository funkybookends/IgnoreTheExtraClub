package com.ignoretheextraclub.service.post;

import com.codahale.metrics.MetricRegistry;
import com.ignoretheextraclub.model.data.Post;
import com.ignoretheextraclub.persistence.PostRepository;
import com.ignoretheextraclub.service.pattern.PatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by caspar on 02/04/17.
 */
@Service
public class PostServiceImpl implements PostService
{
    private final PostRepository postRepository;
    private final MetricRegistry metricRegistry;

    @Autowired
    public PostServiceImpl(final PostRepository postRepository,
            final MetricRegistry metricRegistry)
    {
        this.postRepository = postRepository;
        this.metricRegistry = metricRegistry;
    }

    @Override
    public Optional<Post> getPostByTitle(String title)
    {
        return postRepository.findByTitle(title);
    }

    @Override
    public Optional<Post> getPostById(String id)
    {
        return Optional.ofNullable(postRepository.findOne(id));
    }

    @Override
    public Post save(Post post)
    {
        return postRepository.save(post);
    }

    @Override
    public Page newest(final int page, final int size)
    {
        Pageable pageable = new PageRequest(page,
                size,
                new Sort(Sort.Direction.DESC, "createdDate"));
        return postRepository.findAll(pageable);
    }

    @Override
    public Page newest(int page)
    {
        return newest(page, PatternService.DEFAULT_PAGE_SIZE);
    }
}
