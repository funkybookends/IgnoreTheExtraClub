package com.ignoretheextraclub.vanillasiteswap.converters;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by caspar on 10/12/16.
 */
public class IntHefflishTest
{
    @Test
    public void intToHefflish() throws Exception
    {
        Assert.assertEquals("",     IntHefflish.intToHefflish(-1));
        Assert.assertEquals("gap",  IntHefflish.intToHefflish(0));
        Assert.assertEquals("",     IntHefflish.intToHefflish(1));
        Assert.assertEquals("zip",  IntHefflish.intToHefflish(2));
        Assert.assertEquals("",     IntHefflish.intToHefflish(3));
        Assert.assertEquals("quad", IntHefflish.intToHefflish(12));
        Assert.assertEquals("",     IntHefflish.intToHefflish(13));
        Assert.assertEquals("",     IntHefflish.intToHefflish(15));
    }

}