package com.ignoretheextraclub.vanillasiteswap.sorters.impl;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.PeriodException;
import com.ignoretheextraclub.vanillasiteswap.sorters.IntVanillaStateSorter;
import com.ignoretheextraclub.vanillasiteswap.sorters.SortingUtils;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import org.apache.commons.lang.NotImplementedException;

import java.util.List;
import java.util.function.Function;

/**
 * Created by caspar on 10/12/16.
 */
public class HighestThrowFirstStrategy implements IntVanillaStateSorter
{
    private static final String NAME = "HighestThrowFirst";

    public int sort(final VanillaState[] unsorted) throws InvalidSiteswapException
    {
        throw new NotImplementedException();
    }

    public int sort(final int[] unsorted) throws InvalidSiteswapException
    {
        try
        {
            List<SortingUtils.Rotation> candidates;
            int dist = 0;
            do
            {
                candidates = new SortingUtils.Rotations(unsorted).getMaxesByInt(getMapper(dist));
                dist++;
            }
            while (candidates.size() > 1 && dist < unsorted.length);

            return candidates.get(0).getIndex();

        }
        catch (PeriodException e)
        {
            throw new InvalidSiteswapException("Could not find best start", e);
        }
    }

    private Function<int[], Integer> getMapper(final int dist)
    {
        return thros -> thros[dist];
    }

    @Override
    public String getName()
    {
        return NAME;
    }
}
