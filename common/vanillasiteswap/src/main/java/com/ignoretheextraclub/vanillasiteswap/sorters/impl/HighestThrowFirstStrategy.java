package com.ignoretheextraclub.vanillasiteswap.sorters.impl;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.sorters.IntVanillaStateSorter;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import org.apache.commons.lang.NotImplementedException;

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

    }

    private class Option
    {
        private int startPosition;
        private String value;

        public Option(int startPosition, String value)
        {
            this.startPosition = startPosition;
            this.value = value;
        }

        public int getStartPosition()
        {
            return startPosition;
        }

        public String getValue()
        {
            return value;
        }
    }

    @Override
    public String getName()
    {
        return NAME;
    }
}
