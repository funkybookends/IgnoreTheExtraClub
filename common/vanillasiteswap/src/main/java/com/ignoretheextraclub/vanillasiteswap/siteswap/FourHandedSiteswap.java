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

    public static FourHandedSiteswap create(int[] siteswap, boolean sort) throws InvalidSiteswapException
    {
        return new FourHandedSiteswap(siteswap, sort);
    }

    public static FourHandedSiteswap create(int[] siteswap) throws InvalidSiteswapException
    {
        return new FourHandedSiteswap(siteswap, true);
    }

    public static FourHandedSiteswap create(String siteswap, boolean sort) throws InvalidSiteswapException
    {
        return new FourHandedSiteswap(parseGlobalString(siteswap), sort);
    }

    public static FourHandedSiteswap create(String siteswap) throws InvalidSiteswapException
    {
        return new FourHandedSiteswap(parseGlobalString(siteswap), true);
    }

    private FourHandedSiteswap(int[] vanillaSiteswap, boolean sort) throws InvalidSiteswapException
    {
        super(vanillaSiteswap, sort);
        if (this.getHighestThrow() > MAX_THROW) throw new InvalidSiteswapException("Four Handed Siteswaps Cannot have throws larger than " + MAX_THROW);
        for (int thro : vanillaSiteswap)
        {
            for (int illegalThrow : ILLEGAL_THROWS)
            {
                if (thro == illegalThrow) throw new InvalidSiteswapException("Throw [" + thro + "] is illegal in Four Handed Siteswaps");
            }
        }
    }
}
