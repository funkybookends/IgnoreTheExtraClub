package com.ignoretheextraclub.services.impl;

import com.ignoretheextraclub.model.data.Pattern;
import com.ignoretheextraclub.model.data.PatternName;
import com.ignoretheextraclub.persistence.repository.PatternRepository;
import com.ignoretheextraclub.services.PatternService;
import com.ignoretheextraclub.services.patternconstructors.PatternConstructor;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by caspar on 05/03/17.
 */
@Service
public class PatternServiceImpl implements PatternService
{
    public static final int DEFAULT_SIDE_BAR_SIZE = 10;

    private final PatternRepository        patternRepository;
    private final List<PatternConstructor> constructorList;

    @Autowired
    public PatternServiceImpl(final PatternRepository patternRepository,
                              final List<PatternConstructor> constructorList)
    {
        this.patternRepository = patternRepository;
        this.constructorList = constructorList;
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
            for (PatternConstructor patternConstructor : constructorList)
            {
                Optional<String> naturalName = patternConstructor.getNaturalName(name);

                if (naturalName.isPresent())
                {
                    Optional<Pattern> patternByNaturalName = get(naturalName.get());

                    if (patternByNaturalName.isPresent())
                    {
                        Pattern pattern = patternByNaturalName.get();
                        pattern.setName(new PatternName(name, 0));
                        return patternRepository.save(pattern);
                    }
                    else
                    {
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
        return newest(page, DEFAULT_SIDE_BAR_SIZE);
    }
}
