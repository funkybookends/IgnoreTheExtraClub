package com.ignoretheextraclub.vanillasiteswap.siteswap;

import org.junit.Assert;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by caspar on 27/11/16.
 */
class VanillaTestCase
{
    public final int[] intSiteswap;
    public final String sortedStringSiteswap;
    public final String unsortedStringSiteswap;
    public final Integer numObjects;
    public final Boolean prime;
    public final Boolean grounded;
    public final Integer period;
    public final Integer highestThrow;

    public final boolean validGlobalFHS;
    public final boolean validLocalFHS;
    public final boolean validVanillaSiteswap;


    public VanillaTestCase(int[] constructIntSiteswap,
                           String constructStringSiteswap,
                           String sortedStringSiteswap,
                           Integer numObjects,
                           Boolean prime,
                           Boolean grounded,
                           Integer period,
                           Integer highestThrow,
                           boolean validGlobalFHS,
                           boolean validLocalFHS,
                           boolean validVanillaSiteswap)
    {
        this.intSiteswap = constructIntSiteswap;
        this.unsortedStringSiteswap = constructStringSiteswap;
        this.sortedStringSiteswap = sortedStringSiteswap;
        this.numObjects = numObjects;
        this.prime = prime;
        this.grounded = grounded;
        this.period = period;
        this.highestThrow = highestThrow;

        this.validGlobalFHS = validGlobalFHS;
        this.validLocalFHS = validLocalFHS;
        this.validVanillaSiteswap = validVanillaSiteswap;
    }

    /**
     * Method to verify that the test
     *  @param testMethodName a prefix so you can know which method is being tested for example
     * @param test      the test to test against
     * @param prefix
     */
    public void verify(String testMethodName,
                       String prefix,
                       boolean sorted,
                       VanillaSiteswap test)
    {
        testToString(testMethodName, prefix, sorted, test);

        if (numObjects != null)
            Assert.assertEquals(testMethodName + prefix + this.sortedStringSiteswap + " numObjects",
                    this.numObjects.intValue(), test.getNumObjects());

        if (prime != null)
            Assert.assertEquals(testMethodName + prefix + this.sortedStringSiteswap + " prime",
                    this.prime, test.isPrime());

        if (grounded != null)
            Assert.assertEquals(testMethodName + prefix + this.sortedStringSiteswap + " grounded",
                    this.grounded, test.isGrounded());

        if (period != null)
            Assert.assertEquals(testMethodName + prefix + this.sortedStringSiteswap + " period",
                    this.period.intValue(), test.getPeriod());

        if (highestThrow != null)
            Assert.assertEquals(testMethodName + prefix + this.sortedStringSiteswap + " highestThrow",
                    this.highestThrow.intValue(), test.getHighestThrow());

    }

    public void testToString(String testMethodName,
                             String prefix,
                             boolean sorted,
                             VanillaSiteswap test)
    {
        if (sorted)
        {
            Assert.assertEquals(testMethodName + prefix + this.sortedStringSiteswap + " sorted",
                    this.sortedStringSiteswap, test.toString());
        }
        else
        {
            Assert.assertEquals(testMethodName + prefix + this.unsortedStringSiteswap + " not sorted",
                    this.unsortedStringSiteswap, test.toString());
        }

    }

    public static List<VanillaTestCase> getValidTestCases()
    {
        final LinkedList<VanillaTestCase> valid = new LinkedList<>();

        valid.add(new VanillaTestCase(new int[]{5, 9, 7},
                "597",
                "975",
                7,
                true,
                true,
                3,
                9,
                true,
                false,
                true));

        valid.add(new VanillaTestCase(new int[]{3, 4, 5},
                "345",
                "453",
                4,
                false,
                true,
                3,
                5,
                false,
                false,
                true));

        valid.add(new VanillaTestCase(new int[]{6, 7, 8, 9, 10},
                "6789A",
                "789A6",
                8,
                false,
                true,
                5,
                10,
                true,
                true,
                true));

        valid.add(new VanillaTestCase(new int[]{0,0,3},
                "003",
                "300",
                1,
                true,
                true,
                3,
                3,
                false,
                false,
                true));

        valid.add(new VanillaTestCase(new int[]{8, 6, 7, 8, 6},
                "86786",
                "78686",
                7,
                false,
                true,
                5,
                8,
                true,
                false,
                true));

        valid.add(new VanillaTestCase(new int[]{2, 9, 7},
                "297",
                "972",
                6,
                true,
                false,
                3,
                9,
                true,
                false,
                true));

        valid.add(new VanillaTestCase(new int[]{2, 7, 9},
                "279",
                "972",
                6,
                true,
                false,
                3,
                9,
                false,
                true,
                false));

        return valid;
    }
}
