package com.ignoretheextraclub.services;

import com.ignoretheextraclub.model.Pattern;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;

import java.util.Optional;

/**
 * Created by caspar on 05/03/17.
 */
public interface PatternService
{
    Optional<Pattern> get(String name);

    Pattern getOrCreate(String name) throws InvalidSiteswapException;

    Pattern save(Pattern pattern);
}
