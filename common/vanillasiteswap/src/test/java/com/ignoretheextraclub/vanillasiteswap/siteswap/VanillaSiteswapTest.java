package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.ignoretheextraclub.vanillasiteswap.exceptions.BadThrowException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
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

    /**
     * Valid TestCases to use.
     */
    private List<VanillaTestCase> validSiteswaps = VanillaTestCase.getValidTestCases().stream()
            .filter(vanillaTestCase -> vanillaTestCase.validVanillaSiteswap)
            .collect(Collectors.toList());

    @Test
    public void parseNoSort() throws Exception, InvalidSiteswapException
    {
        final String prefix = "";
        final boolean sorted = false;
        int failures = 0;
        for (VanillaTestCase testCase : validSiteswaps)
        {
            try
            {
                VanillaSiteswap vanillaSiteswap = VanillaSiteswap.create(testCase.intSiteswap, sorted);
                testCase.verify("parseNoSort", prefix, sorted, vanillaSiteswap);
            }
            catch (ComparisonFailure e)
            {
                e.printStackTrace();
                System.out.println(e.getMessage());
                failures++;
            }
        }
        if (failures > 0) Assert.fail("There were " + failures + " failures");

    }

    @Test
    public void parseGlobalStringNoSort() throws InvalidSiteswapException, BadThrowException
    {
        final String prefix = "";
        final boolean sorted = false;
        int failures = 0;
        for (VanillaTestCase testCase : validSiteswaps)
        {
            try
            {
                VanillaSiteswap vanillaSiteswap = VanillaSiteswap.create(testCase.unsortedStringSiteswap, sorted);
                testCase.verify("parseGlobalStringNoSort", prefix, sorted, vanillaSiteswap);
            }
            catch (ComparisonFailure e)
            {
                e.printStackTrace();
                System.out.println(e.getMessage());
                failures++;
            }
        }
        if (failures > 0) Assert.fail("There were " + failures + " failures");
    }

    @Test
    public void parseGlobalStringSorted() throws InvalidSiteswapException, BadThrowException
    {
        final boolean sorted = true;
        final String prefix = "";
        int failures = 0;
        for (VanillaTestCase testCase : validSiteswaps)
        {
            try
            {
                VanillaSiteswap vanillaSiteswap = VanillaSiteswap.create(testCase.unsortedStringSiteswap, sorted);
                testCase.verify("parseGlobalStringSorted", prefix, sorted, vanillaSiteswap);
            }
            catch (ComparisonFailure e)
            {
                e.printStackTrace();
                System.out.println(e.getMessage());
                failures++;
            }
        }
        if (failures > 0) Assert.fail("There were " + failures + " failures");
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
}