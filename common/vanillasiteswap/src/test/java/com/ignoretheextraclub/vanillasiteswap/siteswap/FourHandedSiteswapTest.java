package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by caspar on 27/11/16.
 */
public class FourHandedSiteswapTest
{
    private List<VanillaTestCase> validFHSs;

    @Before
    public void setUp() throws Exception
    {
        validFHSs = VanillaTestCase.getValidTestCases().stream()
                .filter(testCase -> testCase.validGlobalFHS || testCase.validLocalFHS)
                .collect(Collectors.toList());
    }

    @Test
    public void create() throws Exception, InvalidSiteswapException
    {
        final String prefix = "";
        final boolean sorted = true;
        for (VanillaTestCase validFHS : validFHSs)
        {
            FourHandedSiteswap fourHandedSiteswap = FourHandedSiteswap.create(validFHS.intSiteswap, sorted);
            validFHS.verify("create", prefix, sorted, fourHandedSiteswap);
        }
    }

}