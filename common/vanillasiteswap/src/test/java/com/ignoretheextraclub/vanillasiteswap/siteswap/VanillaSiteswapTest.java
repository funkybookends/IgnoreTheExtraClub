package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.sorters.VanillaStateSorter;
import com.ignoretheextraclub.vanillasiteswap.sorters.impl.HighestThrowFirstStrategy;
import com.ignoretheextraclub.vanillasiteswap.state.VanillaState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by caspar on 26/11/16.
 */
public class VanillaSiteswapTest
{
    private static final VanillaStateSorter NO_SORTING_STRATEGY = new VanillaStateSorter()
    {
        @Override
        public String getName()
        {
            return "No Sorting Strategy";
        }

        @Override
        public boolean takeFirst(VanillaState[] first, VanillaState[] second) throws InvalidSiteswapException
        {
            return true;
        }
    };
    private static final VanillaStateSorter HIGHEST_THROW_FIRST_STRATEGY = new HighestThrowFirstStrategy();
    /**
     * Invalid siteswaps to test against
     */
    private String[] invalidSiteswaps = new String[]{
            "543",
            "9768458",
//            "975", // to test that it will throw an exception if given a valid siteswap
    };
    private VanillaSiteswap s975;

    @Before
    public void setUp() throws Exception, InvalidSiteswapException
    {
        s975 = VanillaSiteswap.create("975");
    }

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
        Assert.assertEquals(3, VanillaSiteswap.create(new int[]{9, 7, 5}, NO_SORTING_STRATEGY).getPeriod());
        Assert.assertEquals(1, VanillaSiteswap.create(new int[]{9}, NO_SORTING_STRATEGY).getPeriod());
        Assert.assertEquals(15, VanillaSiteswap.create(new int[]{7,7,7,7,7, 7,7,7,7,7, 7,7,7,7,7}, NO_SORTING_STRATEGY).getPeriod());
        Assert.assertEquals(3, VanillaSiteswap.create(s975.getStates()).getPeriod());
    }

    @Test
    public void testNumJugglers() throws Exception, InvalidSiteswapException
    {
        Assert.assertEquals(1, VanillaSiteswap.create(new int[]{9, 7, 5}, NO_SORTING_STRATEGY).getNumJugglers());
        Assert.assertEquals(1, VanillaSiteswap.create(new int[]{9}, NO_SORTING_STRATEGY).getNumJugglers());
        Assert.assertEquals(1, VanillaSiteswap.create(s975.getStates()).getNumJugglers());
    }

    @Test
    public void testNumObjects() throws InvalidSiteswapException
    {
        Assert.assertEquals(7, VanillaSiteswap.create(new int[]{9, 7, 5}, NO_SORTING_STRATEGY).getNumObjects());
        Assert.assertEquals(9, VanillaSiteswap.create(new int[]{9}, NO_SORTING_STRATEGY).getNumObjects());
        Assert.assertEquals(7, VanillaSiteswap.create(s975.getStates()).getNumObjects());
    }

    @Test
    public void testPrime() throws Exception, InvalidSiteswapException
    {
        Assert.assertEquals(true, VanillaSiteswap.create(new int[]{9, 7, 5}, NO_SORTING_STRATEGY).isPrime());
        Assert.assertEquals(true, VanillaSiteswap.create(new int[]{9}, NO_SORTING_STRATEGY).isPrime());
        Assert.assertEquals(false, VanillaSiteswap.create(new int[]{7,8,6,8,6}, NO_SORTING_STRATEGY).isPrime());
        Assert.assertEquals(true, VanillaSiteswap.create(s975.getStates()).isPrime());
    }

    @Test
    public void testGrounded() throws Exception, InvalidSiteswapException
    {
        Assert.assertEquals(true, VanillaSiteswap.create(new int[]{9, 7, 5}, NO_SORTING_STRATEGY).isGrounded());
        Assert.assertEquals(false, VanillaSiteswap.create(new int[]{9, 7, 2}, NO_SORTING_STRATEGY).isGrounded());
        Assert.assertEquals(true, VanillaSiteswap.create(s975.getStates()).isGrounded());
    }

    @Test
    public void testHighestThrow() throws Exception, InvalidSiteswapException
    {
        Assert.assertEquals(9, VanillaSiteswap.create(new int[]{9,7,5}, NO_SORTING_STRATEGY).getHighestThrow());
        Assert.assertEquals(2, VanillaSiteswap.create(new int[]{2}, NO_SORTING_STRATEGY).getHighestThrow());
        Assert.assertEquals(10, VanillaSiteswap.create(new int[]{6,7,8,9,10}, NO_SORTING_STRATEGY).getHighestThrow());
        Assert.assertEquals(9, VanillaSiteswap.create(s975.getStates()).getHighestThrow());
    }

    @Test
    public void testStringSiteswap() throws Exception, InvalidSiteswapException
    {
        Assert.assertEquals("975", VanillaSiteswap.create(new int[]{9,7,5}, NO_SORTING_STRATEGY).getStringSiteswap());
        Assert.assertEquals("759", VanillaSiteswap.create(new int[]{7,5,9}, NO_SORTING_STRATEGY).getStringSiteswap());
        Assert.assertEquals("597", VanillaSiteswap.create(new int[]{5,9,7}, NO_SORTING_STRATEGY).getStringSiteswap());
        Assert.assertEquals("975", VanillaSiteswap.create(s975.getStates()).getStringSiteswap());
    }

    @Test
    public void testIntSiteswap() throws Exception, InvalidSiteswapException
    {
        Assert.assertArrayEquals(new int[]{9,7,5}, VanillaSiteswap.create(new int[]{9,7,5}, NO_SORTING_STRATEGY).getIntSiteswap());
        Assert.assertArrayEquals(new int[]{7,5,9}, VanillaSiteswap.create(new int[]{7,5,9}, NO_SORTING_STRATEGY).getIntSiteswap());
        Assert.assertArrayEquals(new int[]{5,9,7}, VanillaSiteswap.create(new int[]{5,9,7}, NO_SORTING_STRATEGY).getIntSiteswap());
        Assert.assertArrayEquals(new int[]{9,7,5}, VanillaSiteswap.create(s975.getStates()).getIntSiteswap());

    }

    @Test
    public void testStartingObjectsPerHand() throws Exception, InvalidSiteswapException
    {
        Assert.assertArrayEquals(new int[]{3,2}, VanillaSiteswap.create(new int[]{5}, NO_SORTING_STRATEGY).getStartingObjectsPerHand());
        Assert.assertArrayEquals(new int[]{4,3}, VanillaSiteswap.create(s975.getStates()).getStartingObjectsPerHand());
    }
}