package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.sorters.IntVanillaStateSorter;
import com.ignoretheextraclub.vanillasiteswap.sorters.NoStateSorter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by caspar on 27/11/16.
 */
public class FourHandedSiteswapTest
{
    private static final IntVanillaStateSorter NO_SORTING_STRATEGY = new NoStateSorter();


    private FourHandedSiteswap s975;

    @Before
    public void setUp() throws Exception, InvalidSiteswapException
    {
        s975 = FourHandedSiteswap.create("975", NO_SORTING_STRATEGY);
    }

    @Test
    public void testIsReversible() throws Exception, InvalidSiteswapException
    {
        Assert.assertEquals(true, FourHandedSiteswap.create(new int[]{9,7,5}, NO_SORTING_STRATEGY).isReversible());
        Assert.assertEquals(true, FourHandedSiteswap.create(s975.getStates()).isReversible());
        Assert.assertEquals(false, FourHandedSiteswap.create("97", NO_SORTING_STRATEGY).isReversible());
    }

    @Test
    public void testLeaderStringSiteswap() throws Exception, InvalidSiteswapException
    {
        Assert.assertEquals("957", FourHandedSiteswap.create(new int[]{9,7,5}, NO_SORTING_STRATEGY).getLeaderStringSiteswap());
        Assert.assertEquals("957", FourHandedSiteswap.create(s975.getStates()).getLeaderStringSiteswap());
    }

    @Test
    public void testFollowerStringSiteswap() throws Exception, InvalidSiteswapException
    {
        Assert.assertEquals("795", FourHandedSiteswap.create(new int[]{9,7,5}, NO_SORTING_STRATEGY).getFollowerStringSiteswap());
        Assert.assertEquals("795", FourHandedSiteswap.create(s975.getStates()).getFollowerStringSiteswap());
    }

    @Test
    public void testLeaderPrechac() throws Exception, InvalidSiteswapException
    {
        Assert.assertEquals("4.5p 2.5p 3.5p", FourHandedSiteswap.create(new int[]{9,7,5}, NO_SORTING_STRATEGY).getLeaderPrechac());
        Assert.assertEquals("4.5p 2.5p 3.5p", FourHandedSiteswap.create(s975.getStates()).getLeaderPrechac());
    }

    @Test
    public void testFollowerPrechac() throws Exception, InvalidSiteswapException
    {
        Assert.assertEquals("3.5p 4.5p 2.5p", FourHandedSiteswap.create(new int[]{9,7,5}, NO_SORTING_STRATEGY).getFollowerPrechac());
        Assert.assertEquals("3.5p 4.5p 2.5p", FourHandedSiteswap.create(s975.getStates()).getFollowerPrechac());
    }

}