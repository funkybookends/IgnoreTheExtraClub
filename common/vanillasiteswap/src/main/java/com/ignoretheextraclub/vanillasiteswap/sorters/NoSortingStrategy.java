package com.ignoretheextraclub.vanillasiteswap.sorters;

import com.ignoretheextraclub.vanillasiteswap.state.MultiplexState;
import com.ignoretheextraclub.vanillasiteswap.state.SynchronousState;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;

/**
 * Created by caspar on 11/12/16.
 */
public class NoSortingStrategy extends AbstractSortingStrategy
{
    /**
     * Does not sort
     * @param unsorted
     * @return 0
     */
    @Override
    public int sort(VanillaState[] unsorted)
    {
        return 0;
    }

    /**
     * Does not sort
     * @param unsorted
     * @return 0
     */
    @Override
    public int sort(MultiplexState[] unsorted)
    {
        return 0;
    }

    /**
     * Does not sort
     * @param unsorted
     * @return 0
     */
    @Override
    public int sort(SynchronousState[] unsorted)
    {
        return 0;
    }
}
