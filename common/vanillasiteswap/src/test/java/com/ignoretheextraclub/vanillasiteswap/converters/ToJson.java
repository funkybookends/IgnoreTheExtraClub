package com.ignoretheextraclub.vanillasiteswap.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.vanillasiteswap.siteswap.FourHandedSiteswap;
import com.ignoretheextraclub.vanillasiteswap.siteswap.VanillaSiteswap;
import org.junit.Test;

/**
 * Created by caspar on 10/12/16.
 */
public class ToJson
{
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void fhstojson() throws Exception, InvalidSiteswapException
    {
        FourHandedSiteswap fhs975 = FourHandedSiteswap.create("975");
        VanillaSiteswap db97531 = VanillaSiteswap.create("975");

        String s = objectMapper.writeValueAsString(fhs975);
        String t = objectMapper.writeValueAsString(db97531);

        System.out.println(s);
        System.out.println("");
        System.out.println(t);


    }
}
