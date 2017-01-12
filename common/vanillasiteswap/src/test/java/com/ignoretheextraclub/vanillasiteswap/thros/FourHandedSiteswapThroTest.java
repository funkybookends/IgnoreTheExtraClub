package com.ignoretheextraclub.vanillasiteswap.thros;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by caspar on 07/01/17.
 */
public class FourHandedSiteswapThroTest
{
    @Test
    public void intToHefflish() throws Exception
    {
        Assert.assertEquals("", FourHandedSiteswapThrow.intToHefflish(-1));
        Assert.assertEquals("gap", FourHandedSiteswapThrow.intToHefflish(0));
        Assert.assertEquals("", FourHandedSiteswapThrow.intToHefflish(1));
        Assert.assertEquals("zip", FourHandedSiteswapThrow.intToHefflish(2));
        Assert.assertEquals("", FourHandedSiteswapThrow.intToHefflish(3));
        Assert.assertEquals("quad", FourHandedSiteswapThrow.intToHefflish(12));
        Assert.assertEquals("", FourHandedSiteswapThrow.intToHefflish(13));
        Assert.assertEquals("", FourHandedSiteswapThrow.intToHefflish(15));
    }

    @Test
    public void fourHandedIntToPrechac() throws Exception
    {
        Assert.assertEquals("3.5p", FourHandedSiteswapThrow.fourHandedIntToPrechac(7));
        Assert.assertEquals("3", FourHandedSiteswapThrow.fourHandedIntToPrechac(6));
        Assert.assertEquals("-1.5p", FourHandedSiteswapThrow.fourHandedIntToPrechac(-3));
    }

    @Test
    public void fourHandedIntsToPrechac() throws Exception
    {
        Assert.assertEquals("3.5p 3 4", FourHandedSiteswapThrow.fourHandedIntsToPrechac(new int[] {7, 6, 8}));
        Assert.assertEquals("3.5p 4.5p 1", FourHandedSiteswapThrow.fourHandedIntsToPrechac(new int[] {7,9,2}));
    }

}