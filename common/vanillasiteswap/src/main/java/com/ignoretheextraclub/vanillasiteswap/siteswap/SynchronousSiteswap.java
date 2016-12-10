package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;

/**
 * Created by caspar on 07/12/16.
 */
public class SynchronousSiteswap extends Siteswap
{
    public SynchronousSiteswap(final int numJugglers, final int period, final int numObjects) throws InvalidSiteswapException
    {
        super(numJugglers, period, numObjects);
    }
}
