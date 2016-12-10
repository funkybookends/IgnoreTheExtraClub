package com.ignoretheextraclub.vanillasiteswap.converters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by caspar on 10/12/16.
 */
public class StringMultiplexTest
{
    private String siteswap_35_;
    private List<List<Integer>> list_35_;


    @Before
    public void setUp() throws Exception
    {
        siteswap_35_ = "[35]";
        list_35_ = new LinkedList<>();
        final List<Integer> pairOf3s = new LinkedList<>();
        pairOf3s.add(3);
        pairOf3s.add(5);
        list_35_.add(pairOf3s);
    }

    @Test
    public void stringToThrows() throws Exception
    {
        List<List<Integer>> listList = StringMultiplex.stringToThrows(siteswap_35_);
        Assert.assertEquals("Number of beats", 1, listList.size());
        Assert.assertEquals("Number of throws in first beat", 2, listList.get(0).size());
        Assert.assertEquals("First throw", Integer.valueOf(3), listList.get(0).get(0));
        Assert.assertEquals("Second throw", Integer.valueOf(5), listList.get(0).get(1));
    }

    @Test
    public void throwsToString() throws Exception
    {
        Assert.assertEquals(siteswap_35_, StringMultiplex.throwsToString(list_35_));
    }

}