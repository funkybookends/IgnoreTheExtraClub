package com.ignoretheextraclub.itec.pattern.populators;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ignoretheextraclub.itec.pattern.Pattern;
import com.ignoretheextraclub.itec.pattern.PatternPopulator;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

@Component
public class LinksPatternPopulatorImpl implements PatternPopulator
{
	private final List<LinksPatternPopulator> linksPatternPopulators;

	@Autowired
	public LinksPatternPopulatorImpl(final List<LinksPatternPopulator> linksPatternPopulators)
	{
		this.linksPatternPopulators = linksPatternPopulators;
	}

	@Override
	public void populate(final Pattern.PatternBuilder builder, final Siteswap siteswap)
	{
		final Pattern.Links.LinksBuilder linksBuilder = Pattern.Links.builder();
		linksPatternPopulators.forEach(populator -> populator.populate(linksBuilder, siteswap));
		builder.links(linksBuilder.build());
	}
}
