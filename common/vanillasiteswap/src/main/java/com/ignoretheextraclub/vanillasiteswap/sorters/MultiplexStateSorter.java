package com.ignoretheextraclub.vanillasiteswap.sorters;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.state.MultiplexState;

/**
 * Created by caspar on 15/12/16.
 */
public interface MultiplexStateSorter extends StateSorter
{
    int sort(MultiplexState[] unsorted) throws InvalidSiteswapException;
}
