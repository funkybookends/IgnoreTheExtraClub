package com.ignoretheextraclub.vanillasiteswap.converter;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by caspar on 04/12/16.
 */
public class GlobalLocalTest
{
    @Test
    public void globalToLocal() throws Exception
    {
        Assert.assertArrayEquals(new int[] {9,5,7}, GlobalLocal.globalToLocal(new int[]{9,7,5}, 0));
        Assert.assertArrayEquals(new int[] {7,9,5}, GlobalLocal.globalToLocal(new int[]{9,7,5}, 1));

        Assert.assertArrayEquals(new int[] {7,9,6,8,10}, GlobalLocal.globalToLocal(new int[]{7,8,9,10,6}, 0));
        Assert.assertArrayEquals(new int[] {8,10,7,9,6}, GlobalLocal.globalToLocal(new int[]{7,8,9,10,6}, 1));
    }

    @Test
    public void localToGlobal() throws Exception
    {
        Assert.assertArrayEquals(new int[] {9,7,5}, GlobalLocal.localToGlobal(new int[]{9,5,7}));

        Assert.assertArrayEquals(new int[] {7,8,9,10,6}, GlobalLocal.localToGlobal(new int[]{7,9,6,8,10}));
    }

    @Test
    public void globalToLocalObject() throws Exception
    {
        Assert.assertArrayEquals(new Integer[] {9,5,7}, GlobalLocal.globalToLocal(new Integer[]{9,7,5}, 0));
        Assert.assertArrayEquals(new Integer[] {7,9,5}, GlobalLocal.globalToLocal(new Integer[]{9,7,5}, 1));

        Assert.assertArrayEquals(new Integer[] {7,9,6,8,10}, GlobalLocal.globalToLocal(new Integer[]{7,8,9,10,6}, 0));
        Assert.assertArrayEquals(new Integer[] {8,10,7,9,6}, GlobalLocal.globalToLocal(new Integer[]{7,8,9,10,6}, 1));

    }

    @Test
    public void localToGlobalObject() throws Exception
    {
        Assert.assertArrayEquals(new Integer[] {9,7,5}, GlobalLocal.localToGlobal(new Integer[]{9,5,7}));

        Assert.assertArrayEquals(new Integer[] {7,8,9,10,6}, GlobalLocal.localToGlobal(new Integer[]{7,9,6,8,10}));
    }

}