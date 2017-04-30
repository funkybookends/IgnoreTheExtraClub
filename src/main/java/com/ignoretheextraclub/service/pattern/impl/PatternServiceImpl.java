package com.ignoretheextraclub.service.pattern.impl;

import com.codahale.metrics.MetricRegistry;
import com.ignoretheextraclub.model.juggling.Pattern;
import com.ignoretheextraclub.model.juggling.PatternName;
import com.ignoretheextraclub.persistence.PatternRepository;
import com.ignoretheextraclub.service.activity.ActivityService;
import com.ignoretheextraclub.service.pattern.PatternService;
import com.ignoretheextraclub.service.pattern.constructors.PatternConstructor;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ignoretheextraclub.configuration.MetricsConfiguration.CREATE;
import static com.ignoretheextraclub.configuration.MetricsConfiguration.FAILURE;
import static com.ignoretheextraclub.configuration.MetricsConfiguration.FIND;
import static com.ignoretheextraclub.configuration.MetricsConfiguration.PATTERN;
import static com.ignoretheextraclub.configuration.MetricsConfiguration.SUCCESS;
import static com.ignoretheextraclub.configuration.MetricsConfiguration.VIEW;

/**
 * Created by caspar on 05/03/17.
 */
@Service
public class PatternServiceImpl implements PatternService
{
    private static final Logger LOG = LoggerFactory.getLogger(PatternServiceImpl.class);

    private final PatternRepository patternRepository;
    private final ActivityService activityService;
    private final MetricRegistry metricRegistry;
    private final List<PatternConstructor> constructorList;

    @Autowired
    public PatternServiceImpl(final PatternRepository patternRepository,
                              ActivityService activityService,
                              final List<PatternConstructor> constructorList,
                              final MetricRegistry metricRegistry)
    {
        this.patternRepository = patternRepository;
        this.activityService = activityService;
        this.metricRegistry = metricRegistry;

        constructorList.sort(Comparator.comparing(PatternConstructor::getOrder)); // TODO check if line is redundant thanks to PriorityOrder

        this.constructorList = constructorList;

        LOG.info("Constructor List: {}",
                constructorList.stream()
                               .map(pc -> pc.getClass().getSimpleName())
                               .collect(Collectors.joining(", ")));
    }

    private void populateMetrics(final Pattern pattern)
    {
        pattern.getNames().forEach(this::populateMetrics);
    }

    @Override
    public Optional<Pattern> get(final String name)
    {
        final Optional<Pattern> patternOptional = patternRepository.findByName(
                name);

        metricRegistry.meter(MetricRegistry.name(PATTERN, FIND, patternOptional.isPresent() ? SUCCESS : FAILURE, name)).mark();

        return patternOptional;
    }

    private void populateMetrics(final PatternName patternName)
    {
        final long count = metricRegistry.meter(MetricRegistry.name(PATTERN, VIEW, patternName.getName())).getCount();

        patternName.setUsages(count);
    }

    @Transactional
    @Override
    public Pattern getOrCreate(final String name) throws InvalidSiteswapException
    {
        final Optional<Pattern> patternByName = get(name);
        if (patternByName.isPresent())
        {
            return patternByName.get();
        }
        else
        {
            LOG.info("Cannot find pattern [{}], attempting to create.", name);
            for (PatternConstructor patternConstructor : constructorList)
            {
                Optional<String> naturalName = patternConstructor.getNaturalName(name);

                if (naturalName.isPresent())
                {
                    LOG.info("PatternConstructor [{}] converted [{}] into [{}]", patternByName.getClass().getSimpleName(), name, naturalName.get());

                    Optional<Pattern> patternByNaturalName = get(naturalName.get());

                    if (patternByNaturalName.isPresent())
                    {
                        Pattern pattern = patternByNaturalName.get();
                        pattern.setName(new PatternName(name, 0));

                        LOG.debug("Adding new name [{}] to pattern [{}]", name, naturalName.get());

                        return patternRepository.save(pattern);
                    }
                    else
                    {
                        LOG.info("PatternConstructor [{}] constructing new pattern for [{}]", patternConstructor.getClass().getSimpleName(), naturalName.get());

                        final Pattern pattern = patternRepository.save(patternConstructor.createPattern(name));

                        metricRegistry.meter(MetricRegistry.name(PATTERN, CREATE)).mark();

                        activityService.recordNewPattern(pattern);

                        return pattern;
                    }
                }
            }

            throw new InvalidSiteswapException("Not valid siteswap of any known kind");
        }
    }

    @Override
    public Pattern save(Pattern pattern)
    {
        return patternRepository.save(pattern);
    }

    @Override
    public Page<Pattern> newest(int page, int size)
    {
        final Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "createdDate"));

        return patternRepository.findAll(pageable);
    }

    @Override
    public Page<Pattern> newest(int page)
    {
        return newest(page, DEFAULT_PAGE_SIZE);
    }

    @Override
    public Optional<Pattern> getById(String patternId)
    {
        return Optional.ofNullable(patternRepository.findOne(patternId));
    }
}
