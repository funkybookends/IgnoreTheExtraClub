package com.ignoretheextraclub.itec.pattern.impl;

import java.util.List;


import com.ignoretheextraclub.itec.exception.UnknownPatternTypeException;
import com.ignoretheextraclub.itec.pattern.PatternPopulator;
import com.ignoretheextraclub.itec.pattern.PatternService;
import com.ignoretheextraclub.itec.siteswap.SiteswapService;
import com.ignoretheextraclub.itec.siteswap.impl.SiteswapType;
import com.ignoretheextraclub.itec.ui.Pattern;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

public class PatternServiceImpl implements PatternService
{
	private final SiteswapService siteswapService;
	private final List<PatternPopulator> patternPopulators;

	public PatternServiceImpl(final SiteswapService siteswapService,
	                          final List<PatternPopulator> patternPopulators)
	{
		this.siteswapService = siteswapService;
		this.patternPopulators = patternPopulators;
	}

	@Override
	public Pattern getPattern(final SiteswapType type, final String name) throws InvalidSiteswapException, UnknownPatternTypeException
	{
		return buildPattern(siteswapService.getSiteswap(type, name));
	}

	@Override
	public Pattern getPattern(final String name) throws InvalidSiteswapException
	{
		return buildPattern(siteswapService.getSiteswap(name));
	}

	private Pattern buildPattern(final Siteswap siteswap)
	{
		final Pattern.PatternBuilder patternBuilder = Pattern.builder();
		patternPopulators.forEach(patternPopulator -> patternPopulator.populate(patternBuilder, siteswap));
		return patternBuilder.build();
	}

}
