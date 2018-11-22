package com.ignoretheextraclub.itec.pattern.impl;

import com.ignoretheextraclub.itec.pattern.PatternPopulator;
import com.ignoretheextraclub.itec.siteswap.impl.SiteswapType;
import com.ignoretheextraclub.itec.ui.Pattern;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

public class TypePatternPopulator implements PatternPopulator
{
	@Override
	public void populate(final Pattern.PatternBuilder builder, final Siteswap siteswap)
	{
		builder.type(siteswap.getType());
	}
}
