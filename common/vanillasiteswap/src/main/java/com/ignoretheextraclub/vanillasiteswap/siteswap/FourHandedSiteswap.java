package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ignoretheextraclub.vanillasiteswap.converters.GlobalLocal;
import com.ignoretheextraclub.vanillasiteswap.converters.IntPrechac;
import com.ignoretheextraclub.vanillasiteswap.converters.IntVanilla;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.sorters.SortingStrategy;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * Created by caspar on 27/11/16.
 */
@Immutable
public class FourHandedSiteswap extends VanillaSiteswap
{
    public static final int[] ILLEGAL_THROWS = new int[]{1, 3};
    private static final int MAX_THROW = IntVanilla.charToInt('C');
    private static final SortingStrategy DEFAULT_SORTING_STRATEGY = SortingStrategy.FOUR_HANDED_PASSING_STRATEGY;

    @JsonProperty("is_mirrored")              private final boolean isMirrored;
    @JsonProperty("leader_int_siteswap")      private final int[] leaderIntSiteswap;
    @JsonProperty("follower_int_siteswap")    private final int[] followerIntSiteswap;
    @JsonProperty("leader_string_siteswap")   private final String leaderStringSiteswap;
    @JsonProperty("follower_string_siteswap") private final String followerStringSiteswap;
    @JsonProperty("leader_prechac")           private final String leaderPrechac;
    @JsonProperty("follower_prechac")         private final String followerPrechac;

    protected FourHandedSiteswap(final int numJugglers,
                                 final int period,
                                 final int numObjects,
                                 final int[] intSiteswap,
                                 final VanillaState[] states,
                                 final SortingStrategy sorted,
                                 final boolean prime,
                                 final boolean grounded,
                                 final int highestThrow,
                                 final int[] startingObjectsPerHand,
                                 final String stringSiteswap,
                                 final boolean isMirrored,
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
        this.isMirrored = isMirrored;
        this.leaderIntSiteswap = leaderIntSiteswap;
        this.followerIntSiteswap = followerIntSiteswap;
        this.leaderStringSiteswap = leaderStringSiteswap;
        this.followerStringSiteswap = followerStringSiteswap;
        this.leaderPrechac = leaderPrechac;
        this.followerPrechac = followerPrechac;
    }

    private static FourHandedSiteswap createGlobalOrLocal(int[] siteswap, SortingStrategy sort) throws InvalidSiteswapException
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

    private static FourHandedSiteswap createSiteswapOrPrechac(final String siteswap, final SortingStrategy sort) throws
                                                                                                         InvalidSiteswapException
    {
//        try
//        {
            return createGlobalOrLocal(IntVanilla.stringToIntArray(siteswap), sort);
//        }
//        catch (InvalidSiteswapException invalidAsVanillaSiteswap)
//        {
//            try
//            {
//                return createGlobalOrLocal(IntPrechac.prechacToInt(siteswap), sort);
//            }
//            catch (InvalidSiteswapException invalidAsPrechac)
//            {
//                invalidAsVanillaSiteswap.addSuppressed(invalidAsPrechac);
//                throw new InvalidSiteswapException("Invalid as either VanillaSiteswap or Prechac", invalidAsVanillaSiteswap);
//            }
//        }
    }

    public static FourHandedSiteswap create(int[] siteswap, SortingStrategy sort) throws InvalidSiteswapException
    {
        return FourHandedSiteswap.createGlobalOrLocal(siteswap, sort);
    }

    public static FourHandedSiteswap create(int[] siteswap) throws InvalidSiteswapException
    {
        return FourHandedSiteswap.createGlobalOrLocal(siteswap, DEFAULT_SORTING_STRATEGY);
    }

    public static FourHandedSiteswap create(String siteswap, SortingStrategy sort) throws InvalidSiteswapException
    {
        return FourHandedSiteswap.createSiteswapOrPrechac(siteswap, sort);
    }

    public static FourHandedSiteswap create(String siteswap) throws InvalidSiteswapException
    {
        return FourHandedSiteswap.createSiteswapOrPrechac(siteswap, DEFAULT_SORTING_STRATEGY);
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

    @JsonProperty("prechac")
    public String getPrechac()
    {
        if (isMirrored) return leaderPrechac;
        return IntPrechac.OPEN + leaderPrechac + IntPrechac.SEPERATOR + followerPrechac + IntPrechac.CLOSE;
    }

    protected static class FourHandedSiteswapBuilder extends VanillaSiteswapBuilder
    {
        private int[] leaderIntSiteswap;
        private int[] followerIntSiteswap;
        private String leaderStringSiteswap;
        private String followerStringSiteswap;
        private String leaderPrechac;
        private String followerPrechac;
        private boolean isMirrored;

        public FourHandedSiteswapBuilder(int[] vanillaSiteswap, SortingStrategy sortingStrategy) throws InvalidSiteswapException
        {
            super(vanillaSiteswap, sortingStrategy, 4);

            if (this.highestThrow > MAX_THROW) throw new InvalidSiteswapException("Four Handed Siteswaps Cannot have throws larger than " + MAX_THROW);
            for (int thro : vanillaSiteswap)
            {
                for (int illegalThrow : ILLEGAL_THROWS)
                {
                    if (thro == illegalThrow) throw new InvalidSiteswapException("Throw [" + thro + "] is illegal in Four Handed Siteswaps");
                }
            }

            isMirrored = (vanillaSiteswap.length % 2 == 1);

            leaderIntSiteswap = GlobalLocal.globalToLocal(this.intSiteswap, 0);
            leaderStringSiteswap = IntVanilla.intArrayToString(leaderIntSiteswap);
            followerIntSiteswap = GlobalLocal.globalToLocal(this.intSiteswap, 1);
            followerStringSiteswap = IntVanilla.intArrayToString(followerIntSiteswap);

            leaderPrechac = IntPrechac.fourHandedIntsToPrechac(leaderIntSiteswap);
            followerPrechac = IntPrechac.fourHandedIntsToPrechac(followerIntSiteswap);

        }

        protected FourHandedSiteswap buildFourHandedSiteswap() throws InvalidSiteswapException
        {
            if (startingObjectsPerHand.length > 4)
            {
                throw new InvalidSiteswapException("No humans have more than two hands!");
            }
            return new FourHandedSiteswap(2, period, numObjects, intSiteswap, states, sortingStrategy, prime,
                                          grounded, highestThrow, startingObjectsPerHand, stringSiteswap,
                                          isMirrored, leaderIntSiteswap, leaderStringSiteswap, leaderPrechac, followerIntSiteswap,
                                          followerStringSiteswap, followerPrechac);
        }
    }

}
