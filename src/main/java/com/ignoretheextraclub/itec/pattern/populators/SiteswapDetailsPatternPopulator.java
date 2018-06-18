package com.ignoretheextraclub.itec.pattern.populators;

import com.ignoretheextraclub.itec.pattern.Pattern;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

public interface SiteswapDetailsPatternPopulator
{
	void populate(Pattern.SiteswapDetails.SiteswapDetailsBuilder builder, Siteswap siteswap);
}
