package com.ignoretheextraclub.itec.pattern.impl;

import com.ignoretheextraclub.itec.pattern.PatternPopulator;
import com.ignoretheextraclub.itec.ui.Pattern;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

import lombok.extern.java.Log;

@Log
public class CausalDiagramLinksPatternPopulator implements PatternPopulator
{
	@Override
	public void populate(final Pattern.PatternBuilder builder, final Siteswap siteswap)
	{
		builder.causalDiagramSvg("/p/" + siteswap.getType() + "/" + siteswap.toString() + "/causal-diagram");
	}
}
