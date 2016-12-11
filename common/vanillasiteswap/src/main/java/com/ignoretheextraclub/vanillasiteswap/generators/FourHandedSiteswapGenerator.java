package com.ignoretheextraclub.vanillasiteswap.generators;

import com.ignoretheextraclub.vanillasiteswap.exceptions.NumObjectsException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.StateSizeException;
import com.ignoretheextraclub.vanillasiteswap.siteswap.FourHandedSiteswap;

/**
 * Created by caspar on 11/12/16.
 */
public class FourHandedSiteswapGenerator extends VanillaSiteswapGenerator
{
    public FourHandedSiteswapGenerator(int numObjects,
                                       int maxThrow,
                                       boolean prime,
                                       boolean grounded,
                                       int finalPeriod) throws StateSizeException, NumObjectsException
    {
        super(numObjects, maxThrow, prime, grounded, finalPeriod);
        setNumJugglers(2);
        for (int illegalThrow : FourHandedSiteswap.ILLEGAL_THROWS)
        {
            if (illegalThrow < maxThrow) banThrow(illegalThrow);
        }
    }
}
