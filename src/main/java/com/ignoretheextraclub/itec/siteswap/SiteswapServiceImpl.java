package com.ignoretheextraclub.itec.siteswap;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ignoretheextraclub.itec.exception.UnknownPatternTypeException;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

@Component
public class SiteswapServiceImpl implements SiteswapService
{
	private final Map<SiteswapType, SiteswapFactory> typedConstructors;
	private final SiteswapFactory defaultConstructor;
	private final SiteswapRequestBuilder siteswapRequestBuilder;

	public SiteswapServiceImpl(@Qualifier("typedConstructors") final Map<SiteswapType, SiteswapFactory> typedConstructors,
	                           @Qualifier("defaultSiteswapFactory") final SiteswapFactory defaultSiteswapFactory,
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
