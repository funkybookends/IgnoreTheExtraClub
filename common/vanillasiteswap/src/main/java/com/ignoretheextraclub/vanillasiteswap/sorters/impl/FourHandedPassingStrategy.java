package com.ignoretheextraclub.vanillasiteswap.sorters.impl;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.NoTransitionException;
import com.ignoretheextraclub.vanillasiteswap.thros.FourHandedSiteswapThrow;
import com.ignoretheextraclub.vanillasiteswap.sorters.StateSorter;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import com.ignoretheextraclub.vanillasiteswap.thros.VanillaThrow;

/**
 * Created by caspar on 10/12/16.
 */
public class FourHandedPassingStrategy implements StateSorter<FourHandedSiteswapThrow, VanillaState<FourHandedSiteswapThrow>>
{
    private static final String NAME = "FourHandedPassing";

    private static FourHandedPassingStrategy instance;

    private FourHandedPassingStrategy()
    {
    }

    private int scoreRotation(final VanillaState[] states) throws InvalidSiteswapException
    {
        try
        {
            int score = 0;
            for (int i = 0; i < states.length; i++)
            {
                final int thro = ((VanillaThrow) states[i].getThrow(states[(i + 1) % states.length])).getThro();
                score += (states.length - i) * (thro + (thro % 2));
            }
            return score;
        }
        catch (final NoTransitionException cause)
        {
            throw new InvalidSiteswapException("Expected to score a valid siteswap, could not transition", cause);
        }
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
        return first[0].excitedness() < second[0].excitedness();
    }

    public static StateSorter get()
    {
        if (instance == null)
        {
            instance = new FourHandedPassingStrategy();
        }
        return instance;
    }
}
