package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidFourHandedSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;

/**
 * Created by caspar on 27/11/16.
 */
public class FourHandedSiteswap extends VanillaSiteswap
{
    private final static int[] ILLEGAL_THROWS = new int[]{1,3};
    private static final int MAX_THROW = 12; //C

    public FourHandedSiteswap(int[] vanillaSiteswap, boolean sort) throws InvalidSiteswapException, InvalidFourHandedSiteswapException
    {
        super(vanillaSiteswap, sort);
        if (this.getHighestThrow() > MAX_THROW) throw new InvalidFourHandedSiteswapException("Four Handed Siteswaps Cannot have throws larger than " + MAX_THROW);
        for (int thro : vanillaSiteswap)
        {
            for (int illegalThrow : ILLEGAL_THROWS)
            {
                if (thro == illegalThrow) throw new InvalidFourHandedSiteswapException("Throw [" + thro + "] is illegal in Four Handed Siteswaps");
            }
        }
    }



}
