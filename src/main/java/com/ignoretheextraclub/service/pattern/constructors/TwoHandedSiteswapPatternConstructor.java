package com.ignoretheextraclub.service.pattern.constructors;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedSiteswap;
import org.springframework.stereotype.Service;

/**
 * Created by caspar on 05/03/17.
 */
@Service
public class TwoHandedSiteswapPatternConstructor
        extends SimplePatternConstructor
{
    @Override
    protected TwoHandedSiteswap construct(String name) throws
                                                      InvalidSiteswapException
    {
        return TwoHandedSiteswap.create(name);
    }

    @Override
    public int getPriority()
    {
        return 10;
    }
}
