package com.ignoretheextraclub.vanillasiteswap.sorters;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.state.SynchronousState;

/**
 * Created by caspar on 15/12/16.
 */
public interface SynchronousStateSorter extends SortingStrategy
{
    int sort(SynchronousState[] unsorted) throws InvalidSiteswapException;
}
