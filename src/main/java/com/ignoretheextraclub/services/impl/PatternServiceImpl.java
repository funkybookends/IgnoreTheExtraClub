package com.ignoretheextraclub.services.impl;

import com.ignoretheextraclub.model.Pattern;
import com.ignoretheextraclub.model.PatternName;
import com.ignoretheextraclub.persistence.repository.PatternRepository;
import com.ignoretheextraclub.services.PatternService;
import com.ignoretheextraclub.services.patternconstructors.PatternConstructor;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import org.springframework.beans.factory.annotation.Autowired;
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
}
