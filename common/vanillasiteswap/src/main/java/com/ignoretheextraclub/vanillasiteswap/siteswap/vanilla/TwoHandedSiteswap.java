package com.ignoretheextraclub.vanillasiteswap.siteswap.vanilla;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ignoretheextraclub.vanillasiteswap.exceptions.BadThrowException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.sorters.StateSorter;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import com.ignoretheextraclub.vanillasiteswap.thros.VanillaThrow;

/**
 * Created by caspar on 07/01/17.
 */
@JsonPropertyOrder({
        "global_string_siteswap",
        "global_int_siteswap",
        "num_objects",
        "period",
        "prime",
        "grounded",
        "sorting_strategy",
        "states",
        "global_throws",
        "states",
        "highest_throw",
        "first_hand_objects",
        "second_hand_objects"
})
public class TwoHandedSiteswap extends VanillaStateSiteswap<VanillaThrow, VanillaState<VanillaThrow>>
{
    private static final int NUMBER_OF_HANDS = 2;

    public enum Hand
    {
        FIRST(0), SECOND(1);

        public final int globalStartingHand;

        Hand(int globalStartinHand)
        {
            this.globalStartingHand = globalStartinHand;
        }
    }

    public TwoHandedSiteswap(VanillaState<VanillaThrow> startingState,
                             VanillaThrow[] thros,
                             StateSorter<VanillaThrow, VanillaState<VanillaThrow>> sorter) throws InvalidSiteswapException
    {
        super(startingState, thros, sorter);
    }

    public TwoHandedSiteswap(VanillaState<VanillaThrow> startingState,
                             VanillaThrow[] thros) throws InvalidSiteswapException
    {
        super(startingState, thros);
    }

    @JsonProperty("first_hand_objects")
    public int getFirstStartingHandObjects()
    {
        return getStartingNumberOfObjects(NUMBER_OF_HANDS, Hand.FIRST.globalStartingHand);
    }

    @JsonProperty("second_hand_objects")
    public int getSecondStartingHandObjects()
    {
        return getStartingNumberOfObjects(NUMBER_OF_HANDS, Hand.SECOND.globalStartingHand);
    }

    public static TwoHandedSiteswap create(final int[] siteswap, final StateSorter<VanillaThrow, VanillaState<VanillaThrow>> sorter) throws InvalidSiteswapException
    {
        try
        {
            final VanillaThrow[] vanillaThrows = VanillaThrow.intArrayToVanillaThrowArray(siteswap);
            final VanillaState<VanillaThrow> firstState = VanillaState.getFirstState(
                    VanillaThrow.intArrayToVanillaThrowArray(siteswap), VanillaThrow::get);
            return new TwoHandedSiteswap(firstState, vanillaThrows, sorter);
        }
        catch (final BadThrowException cause)
        {
            throw new InvalidSiteswapException("Could not construct siteswap", cause);
        }
    }

    public static TwoHandedSiteswap create(final int[] siteswap) throws InvalidSiteswapException
    {
        try
        {
            final VanillaThrow[] vanillaThrows = VanillaThrow.intArrayToVanillaThrowArray(siteswap);
            final VanillaState<VanillaThrow> firstState = VanillaState.getFirstState(
                    VanillaThrow.intArrayToVanillaThrowArray(siteswap), VanillaThrow::get);
            return new TwoHandedSiteswap(firstState, vanillaThrows);
        }
        catch (final BadThrowException cause)
        {
            throw new InvalidSiteswapException("Could not construct siteswap", cause);
        }
    }

    public static TwoHandedSiteswap create(final String siteswap, final StateSorter<VanillaThrow, VanillaState<VanillaThrow>> sorter) throws InvalidSiteswapException
    {
        return create(VanillaThrow.stringToIntArray(siteswap), sorter);
    }

    public static TwoHandedSiteswap create(final String siteswap) throws InvalidSiteswapException
    {
        return create(VanillaThrow.stringToIntArray(siteswap));
    }
}
