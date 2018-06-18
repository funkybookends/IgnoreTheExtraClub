package com.ignoretheextraclub.itec.pattern.populators;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ignoretheextraclub.itec.pattern.Pattern;
import com.ignoretheextraclub.itec.pattern.Pattern.SiteswapDetails.SiteswapDetailsBuilder;
import com.ignoretheextraclub.itec.pattern.PatternPopulator;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

@Component
public class SiteswapDetailsPatternPopulatorImpl implements PatternPopulator
{
	private final List<com.ignoretheextraclub.itec.pattern.populators.SiteswapDetailsPatternPopulator> siteswapDetailsPopulators;

	@Autowired
	public SiteswapDetailsPatternPopulatorImpl(final List<com.ignoretheextraclub.itec.pattern.populators.SiteswapDetailsPatternPopulator> siteswapDetailsPopulators)
	{
		this.siteswapDetailsPopulators = siteswapDetailsPopulators;
	}

	@Override
	public void populate(final Pattern.PatternBuilder builder, final Siteswap siteswap)
	{
		final SiteswapDetailsBuilder siteswapDetailsBuilder = Pattern.SiteswapDetails.builder();
		siteswapDetailsPopulators.forEach(populator -> populator.populate(siteswapDetailsBuilder, siteswap));
		builder.siteswapDetails(siteswapDetailsBuilder.build());
	}
}
