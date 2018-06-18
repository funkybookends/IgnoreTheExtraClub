package com.ignoretheextraclub.itec.pattern.populators;

import com.ignoretheextraclub.itec.pattern.Pattern;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

public interface LinksPatternPopulator
{
	void populate(Pattern.Links.LinksBuilder builder, Siteswap siteswap);
}
