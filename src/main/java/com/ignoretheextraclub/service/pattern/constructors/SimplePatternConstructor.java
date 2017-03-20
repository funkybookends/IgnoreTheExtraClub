package com.ignoretheextraclub.service.pattern.constructors;

import com.ignoretheextraclub.model.data.Pattern;
import com.ignoretheextraclub.model.data.PatternName;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.AbstractSiteswap;

import java.util.Optional;

/**
 * Created by caspar on 05/03/17.
 */

/**
 * Uses the constructed pattern's {@link #toString()} method to get the natural name.
 * And creates up to two names, the one used in the call and the natural name.
 */
public abstract class SimplePatternConstructor
        implements PatternConstructor
{
    @Override
    public Optional<String> getNaturalName(final String name)
    {
        try
        {
            return Optional.of(getNaturalName(construct(name)));
        }
        catch (InvalidSiteswapException e)
        {
            return Optional.empty();
        }
    }

    @Override
    public Pattern createPattern(final String name)
    {
        try
        {
            final AbstractSiteswap fourHandedSiteswap = construct(name);
            final Pattern pattern = new Pattern(fourHandedSiteswap, new PatternName(getNaturalName(fourHandedSiteswap),1));

            if (!name.equals(getNaturalName(fourHandedSiteswap)))
            {
                pattern.setName(new PatternName(name, 0));
            }

            return pattern;
        }
        catch (InvalidSiteswapException e)
        {
            return null;
        }

    }

    private String getNaturalName(final AbstractSiteswap siteswap)
    {
        return siteswap.toString();
    }

    protected abstract AbstractSiteswap construct(String name) throws InvalidSiteswapException;
}
