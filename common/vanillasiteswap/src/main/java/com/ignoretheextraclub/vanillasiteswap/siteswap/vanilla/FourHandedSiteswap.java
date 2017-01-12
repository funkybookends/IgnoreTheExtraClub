package com.ignoretheextraclub.vanillasiteswap.siteswap.vanilla;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ignoretheextraclub.vanillasiteswap.converter.GlobalLocal;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.sorters.StateSorter;
import com.ignoretheextraclub.vanillasiteswap.sorters.impl.FourHandedPassingStrategy;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import com.ignoretheextraclub.vanillasiteswap.thros.FourHandedSiteswapThrow;
import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * Created by caspar on 27/11/16.
 */
@Immutable
@JsonPropertyOrder({
        "global_string_siteswap",
        "global_int_siteswap",
        "global_prechac",
        "leader_prechac",
        "follower_prechac",
        "leader_siteswap_string",
        "leader_siteswap_int",
        "follower_siteswap_string",
        "follower_siteswap_int",
        "num_objects",
        "period",
        "prime",
        "grounded",
        "mixed_ability",
        "sorting_strategy",
        "states",
        "global_throws",
        "states",
        "highest_throw",
        "leader_first_hand_objects",
        "leader_second_hand_objects",
        "follower_first_hand_objects",
        "follower_second_hand_objects",
        "leader_throws",
        "follower_throws",
})
public class FourHandedSiteswap extends VanillaStateSiteswap<FourHandedSiteswapThrow, VanillaState<FourHandedSiteswapThrow>>
{
    private static final int LEADER_START_POS = 0;
    private static final int FOLLOWER_START_POS = 1;

    private static final StateSorter DEFAULT_SORTER = FourHandedPassingStrategy.get();

    private static final int NUMBER_OF_HANDS = 4;

    public enum Hand
    {
        LEADER_FIRST(0), FOLLOWER_FIRST(1), LEADER_SECOND(2), FOLLOWER_SECOND(3);

        public final int globalStartingHand;

        Hand(int hand)
        {
            this.globalStartingHand = hand;
        }
    }

    public FourHandedSiteswap(VanillaState<FourHandedSiteswapThrow> startingState,
                                 FourHandedSiteswapThrow[] thros,
                                 StateSorter<FourHandedSiteswapThrow, VanillaState<FourHandedSiteswapThrow>> sorter) throws InvalidSiteswapException
    {
        super(startingState, thros, sorter);
    }

    public FourHandedSiteswap(VanillaState<FourHandedSiteswapThrow> startingState,
                                 FourHandedSiteswapThrow[] thros) throws InvalidSiteswapException
    {
        super(startingState, thros);
    }

    public static FourHandedSiteswap create(final int[] siteswap, StateSorter<FourHandedSiteswapThrow, VanillaState<FourHandedSiteswapThrow>> sorter) throws InvalidSiteswapException
    {
        final FourHandedSiteswapThrow[] thros = FourHandedSiteswapThrow.intArrayToFourHandedSiteswapThrowArray(siteswap);
        final VanillaState<FourHandedSiteswapThrow> firstState = VanillaState.getFirstState(thros, FourHandedSiteswapThrow::get);
        return new FourHandedSiteswap(firstState, thros, sorter);
    }

    public static FourHandedSiteswap create(final int[] siteswap) throws InvalidSiteswapException
    {
        return create(siteswap, DEFAULT_SORTER);
    }

    public static FourHandedSiteswap create(final String siteswap, StateSorter<FourHandedSiteswapThrow, VanillaState<FourHandedSiteswapThrow>> sorter) throws InvalidSiteswapException
    {
        return create(FourHandedSiteswapThrow.stringToIntArray(siteswap), sorter);
    }

    public static FourHandedSiteswap create(final String siteswap) throws InvalidSiteswapException
    {
        return create(siteswap, DEFAULT_SORTER);
    }

    @JsonProperty("leader_siteswap_int")
    public int[] getLeaderIntSiteswap()
    {
        return GlobalLocal.globalToLocal(getGlobalIntSiteswap(), LEADER_START_POS);
    }

    @JsonProperty("follower_siteswap_int")
    public int[] getFollowerIntSiteswap()
    {
        return GlobalLocal.globalToLocal(getGlobalIntSiteswap(), FOLLOWER_START_POS);
    }

    @JsonProperty("leader_siteswap_string")
    public String getLeaderStringSiteswap()
    {
        return FourHandedSiteswapThrow.intArrayToString(getLeaderIntSiteswap());
    }

    @JsonProperty("follower_siteswap_string")
    public String getFollowerStringSiteswap()
    {
        return FourHandedSiteswapThrow.intArrayToString(getFollowerIntSiteswap());
    }

    @JsonProperty("leader_prechac")
    public String getLeaderPrechac()
    {
        return FourHandedSiteswapThrow.fourHandedIntsToPrechac(getLeaderIntSiteswap());
    }

    @JsonProperty("follower_prechac")
    public String getFollowerPrechac()
    {
        return FourHandedSiteswapThrow.fourHandedIntsToPrechac(getFollowerIntSiteswap());
    }

    @JsonProperty("global_prechac")
    public String getPrechac()
    {
        return FourHandedSiteswapThrow.fourHandedIntsToPrechac(getGlobalIntSiteswap());
    }

    @JsonProperty("mixed_ability")
    @JsonPropertyDescription("False if both jugglers juggle the same pattern")
    public boolean isMixedAbility()
    {
        return getGlobalIntSiteswap().length % 2 != 1;
    }

    @JsonProperty("leader_first_hand_objects")
    public int getLeaderStartingFirstHandObjects()
    {
        return getStartingNumberOfObjects(NUMBER_OF_HANDS, Hand.LEADER_FIRST.globalStartingHand);
    }

    @JsonProperty("leader_second_hand_objects")
    public int getLeaderStartingSecondHandObjects()
    {
        return getStartingNumberOfObjects(NUMBER_OF_HANDS, Hand.LEADER_SECOND.globalStartingHand);
    }

    @JsonProperty("follower_first_hand_objects")
    public int getFollowerStartingFirstHandObjects()
    {
        return getStartingNumberOfObjects(NUMBER_OF_HANDS, Hand.FOLLOWER_FIRST.globalStartingHand);
    }

    @JsonProperty("follower_second_hand_objects")
    public int getFollowerStartingSecondHandObjects()
    {
        return getStartingNumberOfObjects(NUMBER_OF_HANDS, Hand.FOLLOWER_SECOND.globalStartingHand);
    }

    @JsonProperty("leader_throws")
    public FourHandedSiteswapThrow[] getLeaderThrows()
    {
        return GlobalLocal.globalToLocal(thros, LEADER_START_POS);
    }

    @JsonProperty("follower_throws")
    public FourHandedSiteswapThrow[] getFollowerThrows()
    {
        return GlobalLocal.globalToLocal(thros, FOLLOWER_START_POS);
    }

    public static int[] getIllegalThrows()
    {
        return FourHandedSiteswapThrow.ILLEGAL_THROWS;
    }

    @Override
    @JsonIgnore
    public String toString()
    {
        return getStringSiteswap();
    }
}
