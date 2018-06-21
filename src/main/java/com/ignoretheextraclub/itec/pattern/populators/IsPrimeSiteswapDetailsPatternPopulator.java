package com.ignoretheextraclub.itec.pattern.populators;

import org.springframework.stereotype.Component;

import com.ignoretheextraclub.itec.pattern.Pattern;
import com.ignoretheextraclub.itec.pattern.PatternPopulator;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

@Component
public class IsPrimeSiteswapDetailsPatternPopulator implements PatternPopulator
{
	@Override
	public void populate(final Pattern.PatternBuilder builder, final Siteswap siteswap)
	{
		builder.prime(siteswap.isPrime());
	}
}
