package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.ignoretheextraclub.vanillasiteswap.exceptions.BadThrowException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.sorters.SortingStrategy;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ComparisonFailure;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by caspar on 26/11/16.
 */
public class VanillaSiteswapTest
{
    /**
     * Invalid siteswaps to test against
     */
    private String[] invalidSiteswaps = new String[]{
            "543",
            "9768458",
//            "975", // to test that it will throw an exception if given a valid siteswap
    };

    @Test
    public void whenInvalidSiteswap_DoesNotConstruct() throws Exception
    {
        for (String invalidSiteswap : invalidSiteswaps)
        {
            try
            {
                VanillaSiteswap.create(invalidSiteswap);
                throw new RuntimeException("[" + invalidSiteswap + "] should've thrown an exception");
            }
            catch (InvalidSiteswapException expected)
            {
                // do nothing
            }
        }
    }

    @Test
    public void testPeriod() throws Exception, InvalidSiteswapException
    {
        Assert.assertEquals(3, VanillaSiteswap.create(new int[]{9, 7, 5}).getPeriod());
        Assert.assertEquals(1, VanillaSiteswap.create(new int[]{9}).getPeriod());
        Assert.assertEquals(15, VanillaSiteswap.create(new int[]{7,7,7,7,7, 7,7,7,7,7, 7,7,7,7,7}).getPeriod());
    }

    @Test
    public void testNumJugglers() throws Exception, InvalidSiteswapException
    {
        Assert.assertEquals(1, VanillaSiteswap.create(new int[]{9, 7, 5}).getNumJugglers());
        Assert.assertEquals(1, VanillaSiteswap.create(new int[]{9}).getNumJugglers());
    }

    @Test
    public void testNumObjects() throws InvalidSiteswapException
    {
        Assert.assertEquals(7, VanillaSiteswap.create(new int[]{9, 7, 5}).getNumObjects());
        Assert.assertEquals(9, VanillaSiteswap.create(new int[]{9}).getNumObjects());
    }

    @Test
    public void testPrime() throws Exception, InvalidSiteswapException
    {
        Assert.assertEquals(true, VanillaSiteswap.create(new int[]{9, 7, 5}).isPrime());
        Assert.assertEquals(true, VanillaSiteswap.create(new int[]{9}).isPrime());
        Assert.assertEquals(false, VanillaSiteswap.create(new int[]{7,8,6,8,6}).isPrime());
    }

    @Test
    public void testGrounded() throws Exception, InvalidSiteswapException
    {
        Assert.assertEquals(true, VanillaSiteswap.create(new int[]{9, 7, 5}).isGrounded());
        Assert.assertEquals(false, VanillaSiteswap.create(new int[]{9, 7, 2}).isGrounded());
    }

    @Test
    public void testHighestThrow() throws Exception, InvalidSiteswapException
    {
        Assert.assertEquals(9, VanillaSiteswap.create(new int[]{9,7,5}).getHighestThrow());
        Assert.assertEquals(2, VanillaSiteswap.create(new int[]{2}).getHighestThrow());
        Assert.assertEquals(10, VanillaSiteswap.create(new int[]{6,7,8,9,10}).getHighestThrow());
    }

    @Test
    public void testStringSiteswap() throws Exception, InvalidSiteswapException
    {
        Assert.assertEquals("975", VanillaSiteswap.create(new int[]{9,7,5}, SortingStrategy.NO_SORTING_STRATEGY).getStringSiteswap());
        Assert.assertEquals("759", VanillaSiteswap.create(new int[]{7,5,9}, SortingStrategy.NO_SORTING_STRATEGY).getStringSiteswap());
        Assert.assertEquals("597", VanillaSiteswap.create(new int[]{5,9,7}, SortingStrategy.NO_SORTING_STRATEGY).getStringSiteswap());
        Assert.assertEquals("975", VanillaSiteswap.create(new int[]{9,7,5}, SortingStrategy.HIGHEST_THROW_FIRST_STRATEGY).getStringSiteswap());
        Assert.assertEquals("975", VanillaSiteswap.create(new int[]{7,5,9}, SortingStrategy.HIGHEST_THROW_FIRST_STRATEGY).getStringSiteswap());
        Assert.assertEquals("975", VanillaSiteswap.create(new int[]{5,9,7}, SortingStrategy.HIGHEST_THROW_FIRST_STRATEGY).getStringSiteswap());
    }

    @Test
    public void testIntSiteswap() throws Exception, InvalidSiteswapException
    {
        Assert.assertArrayEquals(new int[]{9,7,5}, VanillaSiteswap.create(new int[]{9,7,5}, SortingStrategy.NO_SORTING_STRATEGY).getIntSiteswap());
        Assert.assertArrayEquals(new int[]{7,5,9}, VanillaSiteswap.create(new int[]{7,5,9}, SortingStrategy.NO_SORTING_STRATEGY).getIntSiteswap());
        Assert.assertArrayEquals(new int[]{5,9,7}, VanillaSiteswap.create(new int[]{5,9,7}, SortingStrategy.NO_SORTING_STRATEGY).getIntSiteswap());
        Assert.assertArrayEquals(new int[]{9,7,5}, VanillaSiteswap.create(new int[]{9,7,5}, SortingStrategy.HIGHEST_THROW_FIRST_STRATEGY).getIntSiteswap());
        Assert.assertArrayEquals(new int[]{9,7,5}, VanillaSiteswap.create(new int[]{7,5,9}, SortingStrategy.HIGHEST_THROW_FIRST_STRATEGY).getIntSiteswap());
        Assert.assertArrayEquals(new int[]{9,7,5}, VanillaSiteswap.create(new int[]{5,9,7}, SortingStrategy.HIGHEST_THROW_FIRST_STRATEGY).getIntSiteswap());
    }

    @Test
    public void testStartingObjectsPerHand() throws Exception, InvalidSiteswapException
    {
        Assert.assertArrayEquals(new int[]{3,2}, VanillaSiteswap.create(new int[]{5}).getStartingObjectsPerHand());
    }
}