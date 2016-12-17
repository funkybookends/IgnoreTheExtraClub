package com.ignoretheextraclub.vanillasiteswap.sorters;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;

/**
 * Created by caspar on 15/12/16.
 */
public interface StateSorter<T>
{
    String getName();

    boolean takeFirst(T[] first, T[] second) throws InvalidSiteswapException;
}
