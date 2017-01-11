package com.ignoretheextraclub.vanillasiteswap.sorters;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.state.AbstractState;
import com.ignoretheextraclub.vanillasiteswap.thros.AbstractThro;

/**
 * Created by caspar on 15/12/16.
 */
public interface StateSorter<Throw extends AbstractThro, State extends AbstractState<Throw>>
{
    String getName();

    boolean takeFirst(State[] first, State[] second) throws InvalidSiteswapException;
}
