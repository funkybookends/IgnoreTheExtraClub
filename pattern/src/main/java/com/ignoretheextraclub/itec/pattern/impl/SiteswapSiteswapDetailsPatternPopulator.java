package com.ignoretheextraclub.itec.pattern.impl;

import com.ignoretheextraclub.itec.pattern.PatternPopulator;
import com.ignoretheextraclub.itec.ui.Pattern;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

public class SiteswapSiteswapDetailsPatternPopulator implements PatternPopulator
{
	@Override
	public void populate(final Pattern.PatternBuilder builder, final Siteswap siteswap)
	{
		builder.siteswap(siteswap.toString());
	}
}
