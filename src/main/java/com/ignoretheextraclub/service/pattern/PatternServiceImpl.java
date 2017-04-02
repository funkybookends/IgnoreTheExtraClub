package com.ignoretheextraclub.service.pattern;

import com.ignoretheextraclub.model.data.Pattern;
import com.ignoretheextraclub.model.data.PatternName;
import com.ignoretheextraclub.persistence.PatternRepository;
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

/**
 * Created by caspar on 05/03/17.
 */
@Service
public class PatternServiceImpl implements PatternService
{
    private static final Logger LOG                   = LoggerFactory.getLogger(PatternServiceImpl.class);


    private final PatternRepository        patternRepository;
    private final List<PatternConstructor> constructorList;

    @Autowired
    public PatternServiceImpl(final PatternRepository patternRepository,
                              final List<PatternConstructor> constructorList)
    {
        this.patternRepository = patternRepository;
        constructorList.sort(Comparator.comparing(PatternConstructor::getPriority));
        this.constructorList = constructorList;

        LOG.info("ConstructorList: {}",
                 constructorList.stream()
                                .map(pc -> pc.getClass().getSimpleName())
                                .collect(Collectors.joining(", ")));
    }

    @Override
    public Optional<Pattern> get(final String name)
    {
        return patternRepository.findByName(name);
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
                    LOG.info("PatternConstructor [{}] converted [{}] into [{}]",
                             patternByName.getClass().getSimpleName(),
                             name,
                             naturalName.get());

                    Optional<Pattern> patternByNaturalName = get(naturalName.get());

                    if (patternByNaturalName.isPresent())
                    {
                        Pattern pattern = patternByNaturalName.get();
                        pattern.setName(new PatternName(name, 0));

                        LOG.debug("Adding new name [{}] to pattern [{}]",
                                  name,
                                  naturalName.get());

                        return patternRepository.save(pattern);
                    }
                    else
                    {
                        LOG.info("PatternConstructor [{}] constructing new pattern for [{}]",
                                 patternConstructor.getClass().getSimpleName(),
                                 naturalName.get());

                        return patternRepository.save(patternConstructor.createPattern(name));
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
        Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "createdDate"));
        return patternRepository.findAll(pageable);
    }

    @Override
    public Page<Pattern> newest(int page)
    {
        return newest(page, DEFAULT_PAGE_SIZE);
    }
}
