package com.ignoretheextraclub.vanillasiteswap.thros;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by caspar on 07/01/17.
 */
public class VanillaThroTest
{
    @Test
    public void charToInt() throws Exception
    {
        Assert.assertEquals(0, VanillaThro.charToInt('0'));
        Assert.assertEquals(9, VanillaThro.charToInt('9'));
        Assert.assertEquals(10, VanillaThro.charToInt('A'));
        Assert.assertEquals(12, VanillaThro.charToInt('C'));
        Assert.assertEquals(35, VanillaThro.charToInt('Z'));

        Assert.assertEquals(-1, VanillaThro.charToInt(';'));
        Assert.assertEquals(-1, VanillaThro.charToInt('-'));
        Assert.assertEquals(-1, VanillaThro.charToInt(']'));
        Assert.assertEquals(-1, VanillaThro.charToInt('<'));
        Assert.assertEquals(-1, VanillaThro.charToInt('('));
        Assert.assertEquals(-1, VanillaThro.charToInt('+'));
        Assert.assertEquals(-1, VanillaThro.charToInt('/'));
        Assert.assertEquals(-1, VanillaThro.charToInt('"'));
    }



    @Test
    public void intToChar() throws Exception
    {
        Assert.assertEquals('0', VanillaThro.intToChar(0));
        Assert.assertEquals('9', VanillaThro.intToChar(9));
        Assert.assertEquals('A', VanillaThro.intToChar(10));
        Assert.assertEquals('C', VanillaThro.intToChar(12));
        Assert.assertEquals('Z', VanillaThro.intToChar(35));
        Assert.assertEquals('?', VanillaThro.intToChar(-1));
        Assert.assertEquals('?', VanillaThro.intToChar(36));
    }

    @Test
    public void intArrayToCharArray() throws Exception
    {
        Assert.assertArrayEquals(new char[]{'0','A'}, VanillaThro.intArrayToCharArray(new int[]{0,10}));
    }

    @Test
    public void charArrayToIntArray() throws Exception
    {
        Assert.assertArrayEquals(new int[]{0,10}, VanillaThro.charArrayToIntArray(new char[]{'0','A'}));
    }

    @Test
    public void stringToIntArray() throws Exception
    {
        Assert.assertArrayEquals(new int[]{0,10}, VanillaThro.stringToIntArray("0A"));
    }
}