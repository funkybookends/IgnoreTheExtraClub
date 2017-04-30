package com.ignoretheextraclub.service.pattern.constructors;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * Created by caspar on 05/03/17.
 */
@Service
@Order(1)
public class FourHandedSiteswapPatternConstructor extends SimplePatternConstructor
{
    @Override
    protected FourHandedSiteswap construct(final String name) throws InvalidSiteswapException
    {
        return FourHandedSiteswap.create(name);
    }

    @Override
    public int getOrder()
    {
        return 1;
    }
}

