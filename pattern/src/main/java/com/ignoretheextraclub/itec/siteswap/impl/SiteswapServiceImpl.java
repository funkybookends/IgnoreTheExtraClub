package com.ignoretheextraclub.itec.siteswap.impl;

import java.util.Optional;

import javax.inject.Inject;

import com.ignoretheextraclub.itec.ui.Form;
import com.ignoretheextraclub.itec.ui.SortType;
import com.ignoretheextraclub.itec.siteswap.SiteswapService;
import com.ignoretheextraclub.itec.ui.PatternRequest;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.Reducer;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.sorters.StartingStrategy;

public class SiteswapServiceImpl implements SiteswapService
{
	private final SiteswapFactory defaultSiteswapFactory;

	@Inject
	public SiteswapServiceImpl(final SiteswapFactory defaultSiteswapFactory)
	{
		this.defaultSiteswapFactory = defaultSiteswapFactory;
	}

	@Override
	public Siteswap getSiteswap(final PatternRequest patternRequest) throws InvalidSiteswapException
	{
		final Reducer reducer = Optional.ofNullable(patternRequest.getForm())
			.orElse(Form.REDUCED)
			.getReducer();

		final StartingStrategy startingStrategy = Optional.ofNullable(patternRequest.getSort())
			.orElse(SortType.HIGHEST_THROW_FIRST)
			.getStartingStrategy();

		final SiteswapFactory siteswapFactory = Optional.ofNullable(patternRequest.getType())
			.map(SiteswapType::getSiteswapFactory)
			.orElse(defaultSiteswapFactory);

		final SiteswapRequest siteswapRequest = new SiteswapRequestBuilder()
			.withReducer(reducer)
			.withStartingStrategy(startingStrategy)
			.createSiteswapRequest(patternRequest.getSiteswap());

		return siteswapFactory.apply(siteswapRequest);
	}
}
