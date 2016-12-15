package com.ignoretheextraclub.vanillasiteswap.sorters.impl;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.NoTransitionException;
import com.ignoretheextraclub.vanillasiteswap.sorters.IntVanillaStateSorter;
import com.ignoretheextraclub.vanillasiteswap.sorters.SortingUtils;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import org.apache.commons.lang.NotImplementedException;

import java.util.Arrays;

/**
 * Created by caspar on 10/12/16.
 */
public class HighestThrowFirstStrategy implements IntVanillaStateSorter
{
    private static final String NAME = "HighestThrowFirst";
    public int sort(final VanillaState[] unsorted) throws InvalidSiteswapException
    {
        try
        {
            return sort(VanillaState.convert(unsorted));
        }
        catch (final NoTransitionException cause)
        {
            throw new InvalidSiteswapException("Could not convert to int[] for sorting.", cause);
        }
    }

    public int sort(final int[] unsorted) throws NotImplementedException, InvalidSiteswapException
    {
        final int maxThrow = Arrays.stream(unsorted).max().orElse(0);
        final int numMaxThorows = (int) Arrays.stream(unsorted).filter(thro -> thro == maxThrow).count();
        if (numMaxThorows == 1)
        {
            return SortingUtils.getFirstMaxIndex(unsorted);
        }
        else
        {
            throw new NotImplementedException();
        }
    }

    @Override
    public String getName()
    {
        return NAME;
    }
}
