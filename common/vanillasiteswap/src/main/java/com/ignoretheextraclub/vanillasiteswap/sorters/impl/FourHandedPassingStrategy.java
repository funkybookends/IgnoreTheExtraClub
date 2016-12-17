package com.ignoretheextraclub.vanillasiteswap.sorters.impl;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.sorters.VanillaStateSorter;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;

/**
 * Created by caspar on 10/12/16.
 */
public class FourHandedPassingStrategy implements VanillaStateSorter
{
    private static final String NAME = "FourHandedPassing";

    private int scoreRotation(final VanillaState[] states)
    {
        int score = 0;
        for (int i = 0; i < states.length; i++)
        {
            score += states[i].excitedness() * (-i + 1);
        }
        return score;
    }

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public boolean takeFirst(final VanillaState[] first, final VanillaState[] second) throws InvalidSiteswapException
    {
        final int scoreFirst  = scoreRotation(first );
        final int scoreSecond = scoreRotation(second);
        if (scoreFirst > scoreSecond) return true;
        if (scoreFirst < scoreSecond) return false;
        throw new RuntimeException("This should be removed when not debugging. The two starts were equivalent, need to" +
                                           "add way to decide tiebreaks");
    }
}
