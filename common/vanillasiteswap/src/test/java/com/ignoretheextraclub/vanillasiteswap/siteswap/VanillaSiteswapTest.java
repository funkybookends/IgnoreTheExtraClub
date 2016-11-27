package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.ignoretheextraclub.vanillasiteswap.exceptions.BadThrowException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by caspar on 26/11/16.
 */
public class VanillaSiteswapTest
{
    private List<TestCase> validSiteswaps = new LinkedList<>();
    private List<String> invalidSiteswaps = new LinkedList<>();

    @Before
    public void setUp() throws Exception
    {
        validSiteswaps.add(new TestCase(new int[]{5,9,7},"975", "597", 7, true, true, 3));
        validSiteswaps.add(new TestCase(new int[]{3,4,5},"453", "345", 4, false, true, 3));
        validSiteswaps.add(new TestCase(new int[]{6,7,8,9,10},"789A6", "6789A", 8, false, true, 5));
//        validSiteswaps.add(new TestCase(new int[]{0,0,3},"300", "003", 1, true, true, 3));
        validSiteswaps.add(new TestCase(new int[]{8,6,7,8,6},"78686", "86786", 7, false, true, 5));
        validSiteswaps.add(new TestCase(new int[]{2,9,7},"972", "297", 6, true, false, 3));

        invalidSiteswaps.add("543");
        invalidSiteswaps.add("9768458");
//        invalidSiteswaps.add("975"); // to test that it will throw an exception if given a valid siteswap
    }

    @Test
    public void parseNoSort() throws Exception, InvalidSiteswapException
    {
        final String prefix = "parseNoSort";
        final boolean sorted = false;
        for (TestCase testCase : validSiteswaps)
        {
            VanillaSiteswap vanillaSiteswap = new VanillaSiteswap(testCase.intSiteswap, sorted);
            testCase.verify(prefix, vanillaSiteswap, sorted);
        }
    }

    @Test
    public void parseGlobalStringNoSort() throws InvalidSiteswapException, BadThrowException
    {
        final String prefix = "parseGlobalStringNoSort";
        final boolean sorted = false;
        for (TestCase testCase : validSiteswaps)
        {
            VanillaSiteswap vanillaSiteswap = VanillaSiteswap.create(testCase.unsortedStringSiteswap, sorted);
            testCase.verify(prefix, vanillaSiteswap, sorted);
        }
    }

    @Test
    public void parseGlobalStringSorted() throws InvalidSiteswapException, BadThrowException
    {
        final boolean sorted = true;
        final String prefix = "parseGlobalStringSorted";
        for (TestCase testCase : validSiteswaps)
        {
            VanillaSiteswap vanillaSiteswap = VanillaSiteswap.create(testCase.unsortedStringSiteswap, sorted);
            testCase.verify(prefix, vanillaSiteswap, sorted);
        }
    }

    @Test
    public void invalidSiteswapsDontCompile() throws Exception
    {
        for (String invalidSiteswap : invalidSiteswaps)
        {
            try
            {
                VanillaSiteswap.create(invalidSiteswap, true);
                throw new RuntimeException(invalidSiteswap + " should've thrown an exception");
            }
            catch (InvalidSiteswapException e)
            {
                // do nothing
            }
        }


    }


    private static class TestCase
    {
        public final int[] intSiteswap;
        public final String sortedStringSiteswap;
        public final String unsortedStringSiteswap;
        public final int numObjects;
        public final boolean prime;
        public final boolean grounded;
        public final int period;

        public TestCase(int[] unsortedIntSiteswap,
                        String sortedStringSiteswap,
                        String unsortedStringSiteswap, int numObjects,
                        boolean prime,
                        boolean grounded,
                        int period)
        {
            this.intSiteswap = unsortedIntSiteswap;
            this.sortedStringSiteswap = sortedStringSiteswap;
            this.unsortedStringSiteswap = unsortedStringSiteswap;
            this.numObjects = numObjects;
            this.prime = prime;
            this.grounded = grounded;
            this.period = period;
        }
        /**
         * Method to verify that the siteswap
         *
         * @param messagePrefix a prefix so you can know which method is being tested for example
         * @param siteswap the siteswap to test against
         * @param sorted ? test sorted string : test unsorted string;
         */
        public void verify(String messagePrefix, VanillaSiteswap siteswap, boolean sorted)
        {
            messagePrefix += " ";
            if (sorted) Assert.assertEquals(messagePrefix + this.sortedStringSiteswap + " sorted", this.sortedStringSiteswap, siteswap.toString());
            else        Assert.assertEquals(messagePrefix + this.unsortedStringSiteswap + " unsorted", this.unsortedStringSiteswap, siteswap.toString());
            Assert.assertEquals(messagePrefix + this.sortedStringSiteswap + " numObjects", this.numObjects, siteswap.getNumObjects());
            Assert.assertEquals(messagePrefix + this.sortedStringSiteswap + " prime", this.prime, siteswap.isPrime());
            Assert.assertEquals(messagePrefix + this.sortedStringSiteswap + " grounded", this.grounded, siteswap.isGrounded());
            Assert.assertEquals(messagePrefix + this.sortedStringSiteswap + " period", this.period, siteswap.getPeriod());
        }
    }
}