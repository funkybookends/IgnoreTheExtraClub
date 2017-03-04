package com.ignoretheextraclub.persistence;

import com.ignoretheextraclub.model.Pattern;
import com.ignoretheextraclub.siteswapfactory.siteswap.AbstractSiteswap;

import java.util.List;

/**
 * Created by caspar on 06/02/17.
 */
public interface Patterns
{
    void save(Pattern pattern);

    Pattern get(String id, Class<? extends AbstractSiteswap> siteswapType);

    List<Pattern> get(String name);
}
