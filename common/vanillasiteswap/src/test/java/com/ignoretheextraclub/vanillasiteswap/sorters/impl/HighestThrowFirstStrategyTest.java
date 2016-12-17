package com.ignoretheextraclub.vanillasiteswap.sorters.impl;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.siteswap.VanillaSiteswap;
import com.ignoretheextraclub.vanillasiteswap.sorters.SortingUtils;
import com.ignoretheextraclub.vanillasiteswap.sorters.VanillaStateSorter;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by caspar on 17/12/16.
 */
public class HighestThrowFirstStrategyTest
{
    private final HighestThrowFirstStrategy htfs = new HighestThrowFirstStrategy();
    private final VanillaStateSorter noSorting = new VanillaStateSorter()
    {
        @Override
        public String getName()
        {
            return "No Sorting Strategy";
        }

        @Override
        public boolean takeFirst(VanillaState[] first, VanillaState[] second) throws InvalidSiteswapException
        {
            return true;
        }
    };

    @Test
    public void takeFirst() throws Exception, InvalidSiteswapException
    {
        test("A6789");
        test("534");
        test("86867");
    }

    private void test(final String correctRotation) throws InvalidSiteswapException
    {
        final VanillaSiteswap siteswap = VanillaSiteswap.create(correctRotation, noSorting);
        final VanillaState[] expected = siteswap.getStates();
        SortingUtils.Rotations<VanillaState> rotations = new SortingUtils.Rotations<>(expected);
        VanillaState[] actual = rotations.sort(htfs);
        Assert.assertArrayEquals("Test of " + correctRotation, expected, actual);
    }

}