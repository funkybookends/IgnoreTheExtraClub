package com.ignoretheextraclub.vanillasiteswap.siteswap.vanilla;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.sorters.StateSorter;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import com.ignoretheextraclub.vanillasiteswap.thros.VanillaThro;

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
public class TwoHandedSiteswap extends VanillaStateSiteswap<VanillaThro, VanillaState<VanillaThro>>
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

    public TwoHandedSiteswap(VanillaState<VanillaThro> startingState,
                             VanillaThro[] thros,
                             StateSorter<VanillaThro, VanillaState<VanillaThro>> sorter) throws InvalidSiteswapException
    {
        super(startingState, thros, sorter);
    }

    public TwoHandedSiteswap(VanillaState<VanillaThro> startingState,
                             VanillaThro[] thros) throws InvalidSiteswapException
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

    public static TwoHandedSiteswap create(final int[] siteswap, final StateSorter<VanillaThro, VanillaState<VanillaThro>> sorter) throws InvalidSiteswapException
    {
        final VanillaThro[] vanillaThros = VanillaThro.intArrayToVanillaThrowArray(siteswap);
        final VanillaState<VanillaThro> firstState = VanillaState.getFirstState(VanillaThro.intArrayToVanillaThrowArray(siteswap), VanillaThro::get);
        return new TwoHandedSiteswap(firstState, vanillaThros, sorter);
    }

    public static TwoHandedSiteswap create(final int[] siteswap) throws InvalidSiteswapException
    {
        final VanillaThro[] vanillaThros = VanillaThro.intArrayToVanillaThrowArray(siteswap);
        final VanillaState<VanillaThro> firstState = VanillaState.getFirstState(VanillaThro.intArrayToVanillaThrowArray(siteswap), VanillaThro::get);
        return new TwoHandedSiteswap(firstState, vanillaThros);
    }

    public static TwoHandedSiteswap create(final String siteswap, final StateSorter<VanillaThro, VanillaState<VanillaThro>> sorter) throws InvalidSiteswapException
    {
        return create(VanillaThro.stringToIntArray(siteswap), sorter);
    }

    public static TwoHandedSiteswap create(final String siteswap) throws InvalidSiteswapException
    {
        return create(VanillaThro.stringToIntArray(siteswap));
    }
}
