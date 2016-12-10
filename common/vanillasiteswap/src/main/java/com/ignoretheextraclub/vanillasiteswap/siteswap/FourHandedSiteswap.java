package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.ignoretheextraclub.vanillasiteswap.converters.GlobalLocal;
import com.ignoretheextraclub.vanillasiteswap.converters.IntPrechac;
import com.ignoretheextraclub.vanillasiteswap.converters.IntVanilla;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * Created by caspar on 27/11/16.
 */
@Immutable
public class FourHandedSiteswap extends VanillaSiteswap
{
    private final static int[] ILLEGAL_THROWS = new int[]{1,3};
    private static final int MAX_THROW = 12; //C

    private final int[] leaderIntSiteswap;
    private final int[] followerIntSiteswap;
    private final String leaderStringSiteswap;
    private final String followerStringSiteswap;
    private final String leaderPrechac;
    private final String followerPrechac;
    private final String prechac;

    public FourHandedSiteswap(boolean prime,
                              int period,
                              int numObjects,
                              boolean grounded,
                              int highestThrow,
                              boolean sorted,
                              String stringSiteswap,
                              int[] intSiteswap,
                              int[] startingObjectsPerHand,
                              int[] leaderIntSiteswap,
                              int[] followerIntSiteswap,
                              String leaderStringSiteswap,
                              String followerStringSiteswap,
                              String leaderPrechac,
                              String followerPrechac,
                              String prechac)
    {
        super(prime,
              period,
              numObjects,
              grounded,
              highestThrow,
              sorted,
              stringSiteswap,
              intSiteswap,
              startingObjectsPerHand);
        this.leaderIntSiteswap = leaderIntSiteswap;
        this.followerIntSiteswap = followerIntSiteswap;
        this.leaderStringSiteswap = leaderStringSiteswap;
        this.followerStringSiteswap = followerStringSiteswap;
        this.leaderPrechac = leaderPrechac;
        this.followerPrechac = followerPrechac;
        this.prechac = prechac;
    }

    private static FourHandedSiteswap createGlobalOrLocal(int[] siteswap, boolean sort) throws InvalidSiteswapException
    {
        try
        {
            FourHandedSiteswapBuilder builder = new FourHandedSiteswapBuilder(siteswap, sort);
            return builder.buildFourHandedSiteswap();
        }
        catch (InvalidSiteswapException invalidAsGlobal)
        {
            try
            {
                FourHandedSiteswapBuilder builder = new FourHandedSiteswapBuilder(GlobalLocal.localToGlobal(siteswap), sort);
                return builder.buildFourHandedSiteswap();
            }
            catch (InvalidSiteswapException invalidAsLocal)
            {
                throw new InvalidSiteswapException("Invalid as either Global or Local Siteswap");
            }
        }
    }

    public static FourHandedSiteswap create(int[] siteswap, boolean sort) throws InvalidSiteswapException
    {
        return FourHandedSiteswap.createGlobalOrLocal(siteswap, sort);
    }

    public static FourHandedSiteswap create(int[] siteswap) throws InvalidSiteswapException
    {
        return FourHandedSiteswap.createGlobalOrLocal(siteswap, true);
    }

    public static FourHandedSiteswap create(String siteswap, boolean sort) throws InvalidSiteswapException
    {
        return FourHandedSiteswap.createGlobalOrLocal(IntVanilla.stringToIntArray(siteswap), sort);
    }

    public static FourHandedSiteswap create(String siteswap) throws InvalidSiteswapException
    {
        return FourHandedSiteswap.createGlobalOrLocal(IntVanilla.stringToIntArray(siteswap), true);
    }

    protected static class FourHandedSiteswapBuilder extends VanillaSiteswapBuilder
    {
        private int[] leaderIntSiteswap;
        private int[] followerIntSiteswap;
        private String leaderStringSiteswap;
        private String followerStringSiteswap;
        private String leaderPrechac;
        private String followerPrechac;
        private String prechac;

        public FourHandedSiteswapBuilder(int[] vanillaSiteswap, boolean sort) throws InvalidSiteswapException
        {
            super(vanillaSiteswap, sort, 4);

            if (this.highestThrow > MAX_THROW) throw new InvalidSiteswapException("Four Handed Siteswaps Cannot have throws larger than " + MAX_THROW);
            for (int thro : vanillaSiteswap)
            {
                for (int illegalThrow : ILLEGAL_THROWS)
                {
                    if (thro == illegalThrow) throw new InvalidSiteswapException("Throw [" + thro + "] is illegal in Four Handed Siteswaps");
                }
            }

            leaderIntSiteswap = GlobalLocal.globalToLocal(this.intSiteswap, 0);
            leaderStringSiteswap = IntVanilla.intArrayToString(leaderIntSiteswap);
            followerIntSiteswap = GlobalLocal.globalToLocal(this.intSiteswap, 1);
            followerStringSiteswap = IntVanilla.intArrayToString(followerIntSiteswap);
            prechac = IntPrechac.intToPrechac(intSiteswap);
            leaderPrechac = IntPrechac.intToPrechac(leaderIntSiteswap);
            followerPrechac = IntPrechac.intToPrechac(followerIntSiteswap);

        }

        protected FourHandedSiteswap buildFourHandedSiteswap()
        {
            return new FourHandedSiteswap(prime,
                                          period,
                                          numObjects,
                                          grounded,
                                          highestThrow,
                                          sorted,
                                          stringSiteswap,
                                          intSiteswap,
                                          startingObjectsPerHand,
                                          leaderIntSiteswap,
                                          followerIntSiteswap,
                                          leaderStringSiteswap,
                                          followerStringSiteswap,
                                          leaderPrechac,
                                          followerPrechac,
                                          prechac);
        }
    }

}
