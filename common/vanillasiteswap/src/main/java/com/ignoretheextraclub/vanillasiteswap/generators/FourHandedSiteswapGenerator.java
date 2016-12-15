package com.ignoretheextraclub.vanillasiteswap.generators;

import com.ignoretheextraclub.vanillasiteswap.exceptions.NumObjectsException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.StateSizeException;
import com.ignoretheextraclub.vanillasiteswap.siteswap.FourHandedSiteswap;
import org.apache.commons.lang.NotImplementedException;

import java.util.List;

/**
 * Created by caspar on 11/12/16.
 */
public class FourHandedSiteswapGenerator extends VanillaSiteswapGenerator
{
    public FourHandedSiteswapGenerator(int numObjects,
                                       int maxThrow,
                                       boolean prime,
                                       boolean grounded,
                                       int finalPeriod,
                                       int resultLimit) throws StateSizeException, NumObjectsException
    {
        super(numObjects, maxThrow, prime, grounded, finalPeriod, resultLimit);
        setNumJugglers(2);
        for (int illegalThrow : FourHandedSiteswap.getIllegalThrows()) banThrow(illegalThrow);
    }

    public List<FourHandedSiteswap> generateToFourHandedSiteswaps()
    {
        throw new NotImplementedException();
    }

}
