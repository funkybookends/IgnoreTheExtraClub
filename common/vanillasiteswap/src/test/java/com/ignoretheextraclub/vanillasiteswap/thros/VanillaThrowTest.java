package com.ignoretheextraclub.vanillasiteswap.thros;

import com.ignoretheextraclub.vanillasiteswap.exceptions.BadThrowException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by caspar on 07/01/17.
 */
public class VanillaThrowTest
{
    private static final VanillaThrow ZERO = VanillaThrow.getOrNull(0);
    private static final VanillaThrow ONE = VanillaThrow.getOrNull(1);
    private static final VanillaThrow TWO = VanillaThrow.getOrNull(2);
    private static final VanillaThrow THREE = VanillaThrow.getOrNull(3);
    private static final VanillaThrow FOUR = VanillaThrow.getOrNull(4);
    private static final VanillaThrow FIVE = VanillaThrow.getOrNull(5);
    private static final VanillaThrow SIX = VanillaThrow.getOrNull(6);
    private static final VanillaThrow SEVEN = VanillaThrow.getOrNull(7);
    private static final VanillaThrow EIGHT = VanillaThrow.getOrNull(8);

    private static final VanillaThrow[] FIVE_THREE_FOUR = new VanillaThrow[]{FIVE, THREE, FOUR};

    @Test
    public void compareTo() throws Exception
    {
        assertTrue(FIVE.compareTo(THREE) > 0);
        assertTrue(FIVE.compareTo(FIVE) == 0);
        assertTrue(FIVE.compareTo(EIGHT) < 0);
    }

    @Test
    public void getThroAsString() throws Exception
    {
        assertEquals("0", ZERO.toString());
        assertEquals("1", ONE.toString());
        assertEquals("6", SIX.toString());
    }

    @Test
    public void getNumObjectsThrown() throws Exception
    {
        assertEquals(1, ONE.getNumObjectsThrown());
        assertEquals(1, SEVEN.getNumObjectsThrown());
        assertEquals(1, FOUR.getNumObjectsThrown());
        assertEquals(1, TWO.getNumObjectsThrown());

        assertEquals(0, ZERO.getNumObjectsThrown());
    }

    @Test
    public void vanillaThrowArrayToString() throws Exception
    {
        assertEquals("534", VanillaThrow.vanillaThrowArrayToString(FIVE_THREE_FOUR));
    }

    @Test
    public void vanillaThrowArrayToIntArray() throws Exception
    {
        assertArrayEquals(new int[]{5,3,4}, VanillaThrow.vanillaThrowArrayToIntArray(FIVE_THREE_FOUR));
    }

    @Test
    public void intArrayToString() throws Exception
    {
        assertEquals("534", VanillaThrow.intArrayToString(new int[]{5,3,4}));
    }

    @Test
    public void getOrNull() throws Exception
    {
        assertNull(VanillaThrow.getOrNull(-1));
        assertNull(VanillaThrow.getOrNull('?'));
    }

    @Test(expected = BadThrowException.class)
    public void get() throws Exception
    {
        VanillaThrow.get(-1);
    }

    @Test
    public void intArrayToVanillaThrowArray() throws Exception
    {
        assertArrayEquals(FIVE_THREE_FOUR, VanillaThrow.intArrayToVanillaThrowArray(new int[]{5,3,4}));
    }

    @Test
    public void numObjects() throws Exception
    {
        assertEquals(4, VanillaThrow.numObjects(FIVE_THREE_FOUR));
    }

    @Test
    public void getHighestThro() throws Exception
    {
        assertEquals(FIVE, VanillaThrow.getHighestThro(FIVE_THREE_FOUR));
    }

    @Test
    public void stringToVanillaThrowArray() throws Exception
    {
        assertArrayEquals(FIVE_THREE_FOUR, VanillaThrow.stringToVanillaThrowArray("534"));
    }

    @Test
    public void charToInt() throws Exception
    {
        Assert.assertEquals(0, VanillaThrow.charToInt('0'));
        Assert.assertEquals(9, VanillaThrow.charToInt('9'));
        Assert.assertEquals(10, VanillaThrow.charToInt('A'));
        Assert.assertEquals(12, VanillaThrow.charToInt('C'));
        Assert.assertEquals(35, VanillaThrow.charToInt('Z'));

        Assert.assertEquals(-1, VanillaThrow.charToInt(';'));
        Assert.assertEquals(-1, VanillaThrow.charToInt('-'));
        Assert.assertEquals(-1, VanillaThrow.charToInt(']'));
        Assert.assertEquals(-1, VanillaThrow.charToInt('<'));
        Assert.assertEquals(-1, VanillaThrow.charToInt('('));
        Assert.assertEquals(-1, VanillaThrow.charToInt('+'));
        Assert.assertEquals(-1, VanillaThrow.charToInt('/'));
        Assert.assertEquals(-1, VanillaThrow.charToInt('"'));
    }

    @Test
    public void intToChar() throws Exception
    {
        Assert.assertEquals('0', VanillaThrow.intToChar(0));
        Assert.assertEquals('9', VanillaThrow.intToChar(9));
        Assert.assertEquals('A', VanillaThrow.intToChar(10));
        Assert.assertEquals('C', VanillaThrow.intToChar(12));
        Assert.assertEquals('Z', VanillaThrow.intToChar(35));
        Assert.assertEquals('?', VanillaThrow.intToChar(-1));
        Assert.assertEquals('?', VanillaThrow.intToChar(36));
    }

    @Test
    public void intArrayToCharArray() throws Exception
    {
        Assert.assertArrayEquals(new char[]{'0','A'}, VanillaThrow.intArrayToCharArray(new int[]{0,10}));
    }

    @Test
    public void charArrayToIntArray() throws Exception
    {
        Assert.assertArrayEquals(new int[]{0,10}, VanillaThrow.charArrayToIntArray(new char[]{'0','A'}));
    }

    @Test
    public void stringToIntArray() throws Exception
    {
        Assert.assertArrayEquals(new int[]{0,10}, VanillaThrow.stringToIntArray("0A"));
    }
}