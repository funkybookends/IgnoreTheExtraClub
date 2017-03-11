package com.ignoretheextraclub.services.patternconstructors.impl;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import org.springframework.stereotype.Service;

/**
 * Created by caspar on 05/03/17.
 */
@Service
public class FourHandedSiteswapPatternConstructor
        extends SimplePatternConstructor
{

    @Override
    protected FourHandedSiteswap construct(final String name) throws
                                                      InvalidSiteswapException
    {
        return FourHandedSiteswap.create(name);
    }
}

