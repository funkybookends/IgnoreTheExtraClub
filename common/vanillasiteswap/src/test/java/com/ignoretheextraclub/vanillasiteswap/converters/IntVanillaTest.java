package com.ignoretheextraclub.vanillasiteswap.converters;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by caspar on 04/12/16.
 */
public class IntVanillaTest
{
    @Test
    public void charToInt() throws Exception
    {
        Assert.assertEquals(0, IntVanilla.charToInt('0'));
        Assert.assertEquals(9, IntVanilla.charToInt('9'));
        Assert.assertEquals(10, IntVanilla.charToInt('A'));
        Assert.assertEquals(12, IntVanilla.charToInt('C'));
        Assert.assertEquals(35, IntVanilla.charToInt('Z'));

        Assert.assertEquals(-1, IntVanilla.charToInt(';'));
        Assert.assertEquals(-1, IntVanilla.charToInt('-'));
        Assert.assertEquals(-1, IntVanilla.charToInt(']'));
        Assert.assertEquals(-1, IntVanilla.charToInt('<'));
        Assert.assertEquals(-1, IntVanilla.charToInt('('));
        Assert.assertEquals(-1, IntVanilla.charToInt('+'));
        Assert.assertEquals(-1, IntVanilla.charToInt('/'));
        Assert.assertEquals(-1, IntVanilla.charToInt('"'));
    }



    @Test
    public void intToChar() throws Exception
    {
        Assert.assertEquals('0', IntVanilla.intToChar(0));
        Assert.assertEquals('9', IntVanilla.intToChar(9));
        Assert.assertEquals('A', IntVanilla.intToChar(10));
        Assert.assertEquals('C', IntVanilla.intToChar(12));
        Assert.assertEquals('Z', IntVanilla.intToChar(35));
        Assert.assertEquals('?', IntVanilla.intToChar(-1));
        Assert.assertEquals('?', IntVanilla.intToChar(36));
    }

    @Test
    public void intArrayToCharArray() throws Exception
    {
        Assert.assertArrayEquals(new char[]{'0','A'}, IntVanilla.intArrayToCharArray(new int[]{0,10}));
    }

    @Test
    public void charArrayToIntArray() throws Exception
    {
        Assert.assertArrayEquals(new int[]{0,10}, IntVanilla.charArrayToIntArray(new char[]{'0','A'}));
    }

    @Test
    public void stringToIntArray() throws Exception
    {
        Assert.assertArrayEquals(new int[]{0,10}, IntVanilla.stringToIntArray("0A"));
    }

}