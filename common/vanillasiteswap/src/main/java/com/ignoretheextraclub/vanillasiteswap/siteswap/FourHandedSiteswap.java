package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ignoretheextraclub.vanillasiteswap.converters.GlobalLocal;
import com.ignoretheextraclub.vanillasiteswap.converters.IntPrechac;
import com.ignoretheextraclub.vanillasiteswap.converters.IntVanilla;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.sorters.VanillaStateSorter;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * Created by caspar on 27/11/16.
 */
@Immutable
public class FourHandedSiteswap extends VanillaSiteswap
{
    public static final int[] ILLEGAL_THROWS = new int[]{1, 3};
    protected static final int MAX_THROW = IntVanilla.charToInt('C');

    @JsonProperty("reversible")               private final boolean reversible;
    @JsonProperty("leader_int_siteswap")      private final int[] leaderIntSiteswap;
    @JsonProperty("follower_int_siteswap")    private final int[] followerIntSiteswap;
    @JsonProperty("leader_string_siteswap")   private final String leaderStringSiteswap;
    @JsonProperty("follower_string_siteswap") private final String followerStringSiteswap;
    @JsonProperty("leader_prechac")           private final String leaderPrechac;
    @JsonProperty("follower_prechac")         private final String followerPrechac;
    @JsonProperty("prechac")                  private final String prechac;

    @JsonCreator
    protected FourHandedSiteswap(@JsonProperty("num_jugglers") final int numJugglers,
                                 @JsonProperty("period") final int period,
                                 @JsonProperty("num_objects") final int numObjects,
                                 @JsonProperty("int_siteswap") final int[] intSiteswap,
                                 @JsonProperty("states") final VanillaState[] states,
                                 @JsonProperty("prime") final boolean prime,
                                 @JsonProperty("grounded") final boolean grounded,
                                 @JsonProperty("highest_throw") final int highestThrow,
                                 @JsonProperty("starting_objects_per_hand") final int[] startingObjectsPerHand,
                                 @JsonProperty("string_siteswap") final String stringSiteswap,
                                 @JsonProperty("sorting_strategy") final String sortingStrategyName,
                                 @JsonProperty("reversible") final boolean reversible,
                                 @JsonProperty("leader_int_siteswap") final int[] leaderIntSiteswap,
                                 @JsonProperty("leader_string_siteswap") final String leaderStringSiteswap,
                                 @JsonProperty("leader_prechac") final String leaderPrechac,
                                 @JsonProperty("follower_int_siteswap") final int[] followerIntSiteswap,
                                 @JsonProperty("follower_string_siteswap") final String followerStringSiteswap,
                                 @JsonProperty("follower_prechac") final String followerPrechac) throws InvalidSiteswapException
    {
        super(numJugglers,
              period,
              numObjects,
              intSiteswap,
              states,
              prime,
              grounded,
              highestThrow,
              startingObjectsPerHand,
              stringSiteswap,
              sortingStrategyName);
        this.reversible = reversible;
        this.leaderIntSiteswap = leaderIntSiteswap;
        this.followerIntSiteswap = followerIntSiteswap;
        this.leaderStringSiteswap = leaderStringSiteswap;
        this.followerStringSiteswap = followerStringSiteswap;
        this.leaderPrechac = leaderPrechac;
        this.followerPrechac = followerPrechac;
        if (reversible) this.prechac = leaderPrechac;
        else this.prechac = IntPrechac.OPEN + leaderPrechac + IntPrechac.SEPERATOR + followerPrechac + IntPrechac.CLOSE;
    }

    private static FourHandedSiteswap createGlobalOrLocal(int[] siteswap, final VanillaStateSorter sortingStrategy) throws InvalidSiteswapException
    {
        try
        {
            FourHandedSiteswapBuilder builder = new FourHandedSiteswapBuilder(siteswap, sortingStrategy);
            return builder.buildFourHandedSiteswap();
        }
        catch (InvalidSiteswapException invalidAsGlobal)
        {
            try
            {
                FourHandedSiteswapBuilder builder = new FourHandedSiteswapBuilder(GlobalLocal.localToGlobal(siteswap), sortingStrategy);
                return builder.buildFourHandedSiteswap();
            }
            catch (InvalidSiteswapException invalidAsLocal)
            {
                invalidAsGlobal.addSuppressed(invalidAsLocal);
                throw new InvalidSiteswapException("Invalid as either Global or Local Siteswap", invalidAsGlobal);
            }
        }
    }

    private static FourHandedSiteswap createSiteswapOrPrechac(final String siteswap,
                                                              final VanillaStateSorter sortingStrategy) throws InvalidSiteswapException
    {
//        try
//        {
            return createGlobalOrLocal(IntVanilla.stringToIntArray(siteswap), sortingStrategy);
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

    public static FourHandedSiteswap create(int[] siteswap, final VanillaStateSorter sortingStrategy) throws InvalidSiteswapException
    {
        return FourHandedSiteswap.createGlobalOrLocal(siteswap, sortingStrategy);
    }

    public static FourHandedSiteswap create(int[] siteswap) throws InvalidSiteswapException
    {
        return create(siteswap, DEFAULT_SORTING_STRATEGY);
    }

    public static FourHandedSiteswap create(String siteswap, final VanillaStateSorter sortingStrategy) throws InvalidSiteswapException
    {
        return FourHandedSiteswap.createSiteswapOrPrechac(siteswap, sortingStrategy);
    }

    public static FourHandedSiteswap create(String siteswap) throws InvalidSiteswapException
    {
        return create(siteswap, DEFAULT_SORTING_STRATEGY);
    }

    public static FourHandedSiteswap create(VanillaState[] states, final VanillaStateSorter sortingStrategy) throws InvalidSiteswapException
    {
        FourHandedSiteswapBuilder builder = new FourHandedSiteswapBuilder(states);
        return builder.buildFourHandedSiteswap();
    }

    public static FourHandedSiteswap create(VanillaState[] states) throws InvalidSiteswapException
    {
        return create(states, DEFAULT_SORTING_STRATEGY);
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

    public boolean isReversible()
    {
        return reversible;
    }

    public static int[] getIllegalThrows()
    {
        return ILLEGAL_THROWS;
    }
}
