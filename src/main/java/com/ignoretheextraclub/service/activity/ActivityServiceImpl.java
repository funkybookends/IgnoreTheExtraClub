package com.ignoretheextraclub.service.activity;

import com.codahale.metrics.MetricRegistry;
import com.ignoretheextraclub.model.data.Activity;
import com.ignoretheextraclub.model.data.Pattern;
import com.ignoretheextraclub.persistence.ActivityRepository;
import com.ignoretheextraclub.service.pattern.PatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

/**
 * Created by caspar on 02/04/17.
 */
@Service
public class ActivityServiceImpl implements ActivityService
{
    private final ActivityRepository activityRepository;
    private final MetricRegistry metricRegistry;

    @Autowired
    public ActivityServiceImpl(final ActivityRepository activityRepository,
            final MetricRegistry metricRegistry)
    {
        this.activityRepository = activityRepository;
        this.metricRegistry = metricRegistry;
    }

    @Override
    public Optional<Activity> getActivityById(String id)
    {
        return Optional.ofNullable(activityRepository.findOne(id));
    }

    @Override
    public Page newest(final int page, final int size)
    {
        Pageable pageable = new PageRequest(page,
                size,
                new Sort(Sort.Direction.DESC, "createdDate"));
        return activityRepository.findAll(pageable);
    }

    @Override
    public Page newest(int page)
    {
        return newest(page, PatternService.DEFAULT_PAGE_SIZE);
    }

    @Override
    public void recordNewPattern(Pattern pattern)
    {
        final Activity post = new Activity(null,
                pattern.getCreatedDate(),
                pattern.getMiniTitle(),
                pattern.getMiniSubtitle(),
                pattern.getMiniDescription(),
                Collections.singletonList("new-pattern"));

        activityRepository.save(post);
    }
}
