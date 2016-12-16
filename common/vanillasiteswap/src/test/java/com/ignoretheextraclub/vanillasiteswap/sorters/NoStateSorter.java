package com.ignoretheextraclub.vanillasiteswap.sorters;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.state.MultiplexState;
import com.ignoretheextraclub.vanillasiteswap.state.SynchronousState;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import org.apache.commons.lang.NotImplementedException;

/**
 * Created by caspar on 15/12/16.
 */
public class NoStateSorter implements IntVanillaStateSorter, MultiplexStateSorter, SynchronousStateSorter
{
    private static final String NAME = "NoSortingStrategy";
    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public int sort(MultiplexState[] unsorted) throws InvalidSiteswapException
    {
        return 0;
    }

    @Override
    public int sort(SynchronousState[] unsorted) throws InvalidSiteswapException
    {
        return 0;
    }

    @Override
    public int sort(int[] unsorted) throws NotImplementedException, InvalidSiteswapException
    {
        return 0;
    }

    @Override
    public int sort(VanillaState[] unsorted) throws NotImplementedException, InvalidSiteswapException
    {
        return 0;
    }
}
