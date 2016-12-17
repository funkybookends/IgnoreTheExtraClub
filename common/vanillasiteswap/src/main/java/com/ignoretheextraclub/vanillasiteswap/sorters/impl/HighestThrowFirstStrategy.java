package com.ignoretheextraclub.vanillasiteswap.sorters.impl;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.NoTransitionException;
import com.ignoretheextraclub.vanillasiteswap.sorters.VanillaStateSorter;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;

/**
 * Created by caspar on 10/12/16.
 */
public class HighestThrowFirstStrategy implements VanillaStateSorter
{
    private static final String NAME = "HighestThrowFirst";

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public boolean takeFirst(VanillaState[] first, VanillaState[] second) throws InvalidSiteswapException
    {
        try
        {
            for (int i = 0; i < first.length; i++)
            {
                int ftran = VanillaState.transition(first[i] , first[ (i + 1) % first.length ]);
                int stran = VanillaState.transition(second[i], second[(i + 1) % second.length]);
                if (ftran > stran) return true;
                if (ftran < stran) return false;
            }
            return true; //they are equivalent
        }
        catch (NoTransitionException e)
        {
            throw new InvalidSiteswapException("Could not determine transition", e);
        }
    }
}
