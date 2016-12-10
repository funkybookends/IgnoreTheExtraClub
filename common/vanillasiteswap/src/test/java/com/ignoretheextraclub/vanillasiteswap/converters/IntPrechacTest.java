package com.ignoretheextraclub.vanillasiteswap.converters;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by caspar on 10/12/16.
 */
public class IntPrechacTest
{
    @Test
    public void fourHandedIntToPrechac() throws Exception
    {
        Assert.assertEquals("3.5p", IntPrechac.fourHandedIntToPrechac(7));
        Assert.assertEquals("3", IntPrechac.fourHandedIntToPrechac(6));
        Assert.assertEquals("-1.5p", IntPrechac.fourHandedIntToPrechac(-3));
    }

    @Test
    public void fourHandedIntsToPrechac() throws Exception
    {
        Assert.assertEquals("3.5p 3 4", IntPrechac.fourHandedIntsToPrechac(new int[] {7, 6, 8}));
        Assert.assertEquals("3.5p 4.5p 1", IntPrechac.fourHandedIntsToPrechac(new int[] {7,9,2}));
    }

}