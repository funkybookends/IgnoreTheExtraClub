package com.ignoretheextraclub.vanillasiteswap.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.vanillasiteswap.siteswap.vanilla.TwoHandedSiteswap;
import com.ignoretheextraclub.vanillasiteswap.siteswap.vanilla.VanillaStateSiteswap;
import com.ignoretheextraclub.vanillasiteswap.sorters.impl.HighestThrowFirstStrategy;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by caspar on 10/12/16.
 */
public class ToJson
{
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void fhstojson() throws Exception, InvalidSiteswapException
    {
        final String[] fhs = {"6789A"};
        final String[] ths = {"6789A"};

        for (String fh : fhs)
        {
            FourHandedSiteswap fourHandedSiteswap = FourHandedSiteswap.create(fh);
            System.out.println(objectMapper.writeValueAsString(fourHandedSiteswap));
            System.out.println();
        }
        System.out.println();
        for (String fh : ths)
        {
            System.out.println(objectMapper.writeValueAsString(TwoHandedSiteswap.create(fh)));
            System.out.println();
        }


    }
}
