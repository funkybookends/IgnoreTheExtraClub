package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.ignoretheextraclub.vanillasiteswap.converters.GlobalLocal;
import com.ignoretheextraclub.vanillasiteswap.converters.IntPrechac;
import com.ignoretheextraclub.vanillasiteswap.converters.IntVanilla;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.List;

/**
 * Created by caspar on 27/11/16.
 */
@Immutable
public class FourHandedSiteswap extends VanillaSiteswap
{
    private static final int[] ILLEGAL_THROWS = new int[]{1, 3};
    private static final int MAX_THROW = IntVanilla.charToInt('C');

    private final int[] leaderIntSiteswap;
    private final int[] followerIntSiteswap;
    private final String leaderStringSiteswap;
    private final String followerStringSiteswap;
    private final String leaderPrechac;
    private final String followerPrechac;
    private final String prechac;

    protected FourHandedSiteswap(final int numJugglers,
                                 final int period,
                                 final int numObjects,
                                 final int[] intSiteswap,
                                 final List<VanillaState> states,
                                 final boolean sorted,
                                 final boolean prime,
                                 final boolean grounded,
                                 final int highestThrow,
                                 final int[] startingObjectsPerHand,
                                 final String stringSiteswap,
                                 final String prechac,
                                 final int[] leaderIntSiteswap,
                                 final String leaderStringSiteswap,
                                 final String leaderPrechac,
                                 final int[] followerIntSiteswap,
                                 final String followerStringSiteswap,
                                 final String followerPrechac) throws InvalidSiteswapException
    {
        super(numJugglers,
              period,
              numObjects,
              intSiteswap,
              states,
              sorted,
              prime,
              grounded,
              highestThrow,
              startingObjectsPerHand,
              stringSiteswap
        );
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
                invalidAsGlobal.addSuppressed(invalidAsLocal);
                throw new InvalidSiteswapException("Invalid as either Global or Local Siteswap", invalidAsGlobal);
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

    public int[] getLeaderIntSiteswap()
    {
        return leaderIntSiteswap;
    }

    public int[] getFollowerIntSiteswap()
    {
        return followerIntSiteswap;
    }

    public String getLeaderStringSiteswap()
    {
        return leaderStringSiteswap;
    }

    public String getFollowerStringSiteswap()
    {
        return followerStringSiteswap;
    }

    public String getLeaderPrechac()
    {
        return leaderPrechac;
    }

    public String getFollowerPrechac()
    {
        return followerPrechac;
    }

    public String getPrechac()
    {
        return prechac;
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

        protected FourHandedSiteswap buildFourHandedSiteswap() throws InvalidSiteswapException
        {
            if (startingObjectsPerHand.length > 4)
            {
                throw new InvalidSiteswapException("No humans have more than two hands!");
            }
            return new FourHandedSiteswap(2, period, numObjects, intSiteswap, states, sorted,
                                          prime,grounded, highestThrow, startingObjectsPerHand, stringSiteswap, prechac,
                                          leaderIntSiteswap, leaderStringSiteswap, leaderPrechac, followerIntSiteswap,
                                          followerStringSiteswap, followerPrechac);
        }
    }

}
