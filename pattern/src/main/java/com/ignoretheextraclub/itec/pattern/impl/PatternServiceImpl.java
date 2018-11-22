package com.ignoretheextraclub.itec.pattern.impl;

import java.util.List;

import javax.inject.Inject;

import com.ignoretheextraclub.itec.pattern.PatternPopulator;
import com.ignoretheextraclub.itec.pattern.PatternService;
import com.ignoretheextraclub.itec.siteswap.SiteswapService;
import com.ignoretheextraclub.itec.ui.Pattern;
import com.ignoretheextraclub.itec.ui.PatternRequest;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

public class PatternServiceImpl implements PatternService
{
	private final SiteswapService siteswapService;
	private final List<PatternPopulator> patternPopulators;

	@Inject
	public PatternServiceImpl(final SiteswapService siteswapService,
	                          final List<PatternPopulator> patternPopulators)
	{
		this.siteswapService = siteswapService;
		this.patternPopulators = patternPopulators;
	}

	@Override
	public Pattern getPattern(final PatternRequest patternRequest) throws InvalidSiteswapException
	{
		final Siteswap siteswap = siteswapService.getSiteswap(patternRequest);
		final Pattern.PatternBuilder builder = Pattern.builder();

		patternPopulators.parallelStream().unordered().forEach(patternPopulator -> patternPopulator.populate(builder, siteswap));

		return builder.build();
	}
}
