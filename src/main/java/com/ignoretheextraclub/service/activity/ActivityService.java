package com.ignoretheextraclub.service.activity;

import com.ignoretheextraclub.model.data.Activity;
import com.ignoretheextraclub.model.data.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by caspar on 02/04/17.
 */
@Service
public interface ActivityService
{
    Optional<Activity> getActivityById(String id);

    Page newest(int page);

    Page newest(int page, int size);

    void recordNewPattern(Pattern pattern);
}
