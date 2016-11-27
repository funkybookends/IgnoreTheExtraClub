package com.ignoretheextraclub.vanillasiteswap.siteswap;

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

    @Before
    public void setUp() throws Exception
    {
        validSiteswaps.add(new TestCase(new int[]{5,9,7},"975", "597", 7, true, true, 3));
        validSiteswaps.add(new TestCase(new int[]{3,4,5},"453", "345", 4, false, true, 3));
        validSiteswaps.add(new TestCase(new int[]{6,7,8,9,10},"789A6", "6789A", 8, false, true, 5));
//        validSiteswaps.add(new TestCase(new int[]{0,0,3},"300", "003", 1, true, true, 3));
        validSiteswaps.add(new TestCase(new int[]{8,6,7,8,6},"78686", "86786", 7, false, true, 5));
        validSiteswaps.add(new TestCase(new int[]{2,9,7},"972", "297", 6, true, false, 3));
    }

    @Test
    public void parseNoSort() throws Exception, InvalidSiteswapException
    {
        final String prefix = "parseNoSort";
        final boolean sorted = false;
        for (TestCase testCase : validSiteswaps)
        {
            VanillaSiteswap vanillaSiteswap = VanillaSiteswap.parse(testCase.intSiteswap, sorted);
            testCase.verify(prefix, vanillaSiteswap, sorted);
        }
    }

    @Test
    public void parseGlobalStringNoSort() throws InvalidSiteswapException
    {
        final String prefix = "parseGlobalStringNoSort";
        final boolean sorted = false;
        for (TestCase testCase : validSiteswaps)
        {
            VanillaSiteswap vanillaSiteswap = VanillaSiteswap.parse(testCase.unsortedStringSiteswap, sorted);
            testCase.verify(prefix, vanillaSiteswap, sorted);
        }
    }

    @Test
    public void parseGlobalStringSorted() throws InvalidSiteswapException
    {
        final boolean sorted = true;
        final String prefix = "parseGlobalStringSorted";
        for (TestCase testCase : validSiteswaps)
        {
            VanillaSiteswap vanillaSiteswap = VanillaSiteswap.parse(testCase.unsortedStringSiteswap, sorted);
            testCase.verify(prefix, vanillaSiteswap, sorted);
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

        public void verify(String prefix, VanillaSiteswap constructed, boolean sorted)
        {
            prefix += " ";
            if (sorted) Assert.assertEquals(prefix + this.sortedStringSiteswap + " sorted", this.sortedStringSiteswap, constructed.toString());
            else        Assert.assertEquals(prefix + this.unsortedStringSiteswap + " unsorted", this.unsortedStringSiteswap, constructed.toString());
            Assert.assertEquals(prefix + this.sortedStringSiteswap + " numObjects", this.numObjects, constructed.getNumObjects());
            Assert.assertEquals(prefix + this.sortedStringSiteswap + " prime", this.prime, constructed.isPrime());
            Assert.assertEquals(prefix + this.sortedStringSiteswap + " grounded", this.grounded, constructed.isGrounded());
            Assert.assertEquals(prefix + this.sortedStringSiteswap + " period", this.period, constructed.getPeriod());
        }
    }

}