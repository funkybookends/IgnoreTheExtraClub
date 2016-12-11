package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by caspar on 27/11/16.
 */
public class FourHandedSiteswapTest
{
    private FourHandedSiteswap s975;

    @Before
    public void setUp() throws Exception, InvalidSiteswapException
    {
        s975 = FourHandedSiteswap.create("975");
    }

    @Test
    public void testIsMirrored() throws Exception, InvalidSiteswapException
    {
        Assert.assertEquals(true, FourHandedSiteswap.create(new int[]{9,7,5}).isMirrored());
        Assert.assertEquals(true, FourHandedSiteswap.create(s975.getStates()).isMirrored());
        Assert.assertEquals(false, FourHandedSiteswap.create("97").isMirrored());
    }

    @Test
    public void testLeaderStringSiteswap() throws Exception, InvalidSiteswapException
    {
        Assert.assertEquals("957", FourHandedSiteswap.create(new int[]{9,7,5}).getLeaderStringSiteswap());
        Assert.assertEquals("957", FourHandedSiteswap.create(s975.getStates()).getLeaderStringSiteswap());
    }

    @Test
    public void testFollowerStringSiteswap() throws Exception, InvalidSiteswapException
    {
        Assert.assertEquals("795", FourHandedSiteswap.create(new int[]{9,7,5}).getFollowerStringSiteswap());
        Assert.assertEquals("795", FourHandedSiteswap.create(s975.getStates()).getFollowerStringSiteswap());
    }

    @Test
    public void testLeaderPrechac() throws Exception, InvalidSiteswapException
    {
        Assert.assertEquals("4.5p 2.5p 3.4p", FourHandedSiteswap.create(new int[]{9,7,5}).getLeaderPrechac());
        Assert.assertEquals("4.5p 2.5p 3.4p", FourHandedSiteswap.create(s975.getStates()).getLeaderPrechac());
    }

    @Test
    public void testFollowerPrechac() throws Exception, InvalidSiteswapException
    {
        Assert.assertEquals("3.5p 4.5p 2.5p", FourHandedSiteswap.create(new int[]{9,7,5}).getFollowerPrechac());
        Assert.assertEquals("3.5p 4.5p 2.5p", FourHandedSiteswap.create(s975.getStates()).getFollowerPrechac());
    }

}