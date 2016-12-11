package com.ignoretheextraclub.vanillasiteswap.sorters;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.NoTransitionException;
import com.ignoretheextraclub.vanillasiteswap.state.MultiplexState;
import com.ignoretheextraclub.vanillasiteswap.state.SynchronousState;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import org.apache.commons.lang.NotImplementedException;

/**
 * Created by caspar on 10/12/16.
 */
public class HighestThrowFirstStrategy extends AbstractSortingStrategy
{
    public int sort(final VanillaState[] unsorted) throws InvalidSiteswapException
    {
        final int[] intSiteswap = new int[unsorted.length];

        try
        {
            for (int i = 0; i < unsorted.length; i++)
            {
                intSiteswap[i] = VanillaState.transition(unsorted[i], unsorted[(i + 1) % unsorted.length]);
            }
        }
        catch (NoTransitionException e)
        {
            throw new InvalidSiteswapException("Not a valid Siteswap", e);
        }

        return getFirstMaxIndex(intSiteswap);
    }

    public int sort(final MultiplexState[] unsorted)
    {
        throw new NotImplementedException();
    }

    public int sort(final SynchronousState unsorted)
    {
        throw new NotImplementedException();
    }

    @Override
    public String toString()
    {
        return getClass().getName();
    }
}
