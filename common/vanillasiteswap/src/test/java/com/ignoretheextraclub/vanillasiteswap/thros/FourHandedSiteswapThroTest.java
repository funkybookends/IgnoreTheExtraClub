package com.ignoretheextraclub.vanillasiteswap.thros;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by caspar on 07/01/17.
 */
public class FourHandedSiteswapThroTest
{
    @Test
    public void intToHefflish() throws Exception
    {
        Assert.assertEquals("", FourHandedSiteswapThro.intToHefflish(-1));
        Assert.assertEquals("gap",  FourHandedSiteswapThro.intToHefflish(0));
        Assert.assertEquals("",     FourHandedSiteswapThro.intToHefflish(1));
        Assert.assertEquals("zip",  FourHandedSiteswapThro.intToHefflish(2));
        Assert.assertEquals("",     FourHandedSiteswapThro.intToHefflish(3));
        Assert.assertEquals("quad", FourHandedSiteswapThro.intToHefflish(12));
        Assert.assertEquals("",     FourHandedSiteswapThro.intToHefflish(13));
        Assert.assertEquals("",     FourHandedSiteswapThro.intToHefflish(15));
    }

    @Test
    public void fourHandedIntToPrechac() throws Exception
    {
        Assert.assertEquals("3.5p", FourHandedSiteswapThro.fourHandedIntToPrechac(7));
        Assert.assertEquals("3", FourHandedSiteswapThro.fourHandedIntToPrechac(6));
        Assert.assertEquals("-1.5p", FourHandedSiteswapThro.fourHandedIntToPrechac(-3));
    }

    @Test
    public void fourHandedIntsToPrechac() throws Exception
    {
        Assert.assertEquals("3.5p 3 4", FourHandedSiteswapThro.fourHandedIntsToPrechac(new int[] {7, 6, 8}));
        Assert.assertEquals("3.5p 4.5p 1", FourHandedSiteswapThro.fourHandedIntsToPrechac(new int[] {7,9,2}));
    }

}