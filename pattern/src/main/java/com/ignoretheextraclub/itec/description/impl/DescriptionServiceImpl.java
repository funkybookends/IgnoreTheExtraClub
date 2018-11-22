package com.ignoretheextraclub.itec.description.impl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.ignoretheextraclub.itec.description.DescriptionService;
import com.ignoretheextraclub.siteswapfactory.describer.Description;
import com.ignoretheextraclub.siteswapfactory.describer.SiteswapDescriber;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

public class DescriptionServiceImpl implements DescriptionService
{
	private final Map<String, SiteswapDescriber> typeToDescriberMap = new HashMap<>();
	private final SiteswapDescriber defaultDescriber;

	@Inject
	public DescriptionServiceImpl(final SiteswapDescriber fourHandedSiteswapDescriber,
	                              final SiteswapDescriber passingDescriber,
	                              final SiteswapDescriber defaultDescriber)
	{
		this.defaultDescriber = defaultDescriber;
		typeToDescriberMap.put("Four Handed Siteswap", fourHandedSiteswapDescriber);
		typeToDescriberMap.put("Synchronous Passing Siteswap", passingDescriber);
	}

	@Override
	public Map<Locale, Description> describe(final Siteswap siteswap)
	{
		final SiteswapDescriber describer = typeToDescriberMap.getOrDefault(siteswap.getType(), defaultDescriber);

		return describer.getAvailableLocales().stream()
			.map(locale -> describer.describe(siteswap, locale))
			.collect(Collectors.toMap(Description::getLocale, Function.identity()));
	}

}
