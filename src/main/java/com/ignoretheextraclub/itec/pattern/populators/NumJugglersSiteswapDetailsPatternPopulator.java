package com.ignoretheextraclub.itec.pattern.populators;

import org.springframework.stereotype.Component;

import com.ignoretheextraclub.itec.pattern.Pattern;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

@Component
public class NumJugglersSiteswapDetailsPatternPopulator implements SiteswapDetailsPatternPopulator
{
	@Override
	public void populate(final Pattern.SiteswapDetails.SiteswapDetailsBuilder builder, final Siteswap siteswap)
	{
		builder.numJugglers(siteswap.getNumJugglers());
	}
}
