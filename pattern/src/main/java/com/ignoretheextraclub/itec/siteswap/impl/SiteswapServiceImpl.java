package com.ignoretheextraclub.itec.siteswap.impl;

import java.util.Map;
import java.util.Optional;

import com.ignoretheextraclub.itec.exception.UnknownPatternTypeException;
import com.ignoretheextraclub.itec.siteswap.SiteswapService;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

public class SiteswapServiceImpl implements SiteswapService
{
	private final Map<SiteswapType, SiteswapFactory> typedConstructors;
	private final SiteswapFactory defaultConstructor;
	private final SiteswapRequestBuilder siteswapRequestBuilder;

	public SiteswapServiceImpl(final Map<SiteswapType, SiteswapFactory> typedConstructors,
	                           final SiteswapFactory defaultSiteswapFactory,
	                           final SiteswapRequestBuilder siteswapRequestBuilder)
	{
		this.typedConstructors = typedConstructors;
		this.defaultConstructor = defaultSiteswapFactory;
		this.siteswapRequestBuilder = siteswapRequestBuilder;
	}

	@Override
	public Siteswap getSiteswap(final SiteswapType type, final String name) throws InvalidSiteswapException, UnknownPatternTypeException
	{
		return Optional.ofNullable(typedConstructors.get(type))
			.orElseThrow(UnknownPatternTypeException::new)
			.apply(siteswapRequestBuilder.createSiteswapRequest(name));
	}

	@Override
	public Siteswap getSiteswap(final String name) throws InvalidSiteswapException
	{
		return defaultConstructor.apply(siteswapRequestBuilder.createSiteswapRequest(name));
	}
}
