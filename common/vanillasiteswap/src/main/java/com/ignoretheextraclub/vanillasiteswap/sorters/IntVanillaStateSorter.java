package com.ignoretheextraclub.vanillasiteswap.sorters;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import org.apache.commons.lang.NotImplementedException;

/**
 * Created by caspar on 15/12/16.
 */
public interface IntVanillaStateSorter extends SortingStrategy
{
    int sort(int[] unsorted) throws NotImplementedException, InvalidSiteswapException;

    int sort(VanillaState[] unsorted) throws NotImplementedException, InvalidSiteswapException;
}
