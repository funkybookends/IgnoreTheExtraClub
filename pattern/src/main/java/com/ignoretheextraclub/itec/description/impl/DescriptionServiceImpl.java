package com.ignoretheextraclub.itec.description.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ignoretheextraclub.itec.description.DescriptionService;
import com.ignoretheextraclub.itec.siteswap.impl.SiteswapType;
import com.ignoretheextraclub.siteswapfactory.describer.Description;
import com.ignoretheextraclub.siteswapfactory.describer.SiteswapDescriber;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

public class DescriptionServiceImpl implements DescriptionService
{
	private final Map<SiteswapType, SiteswapDescriber> typeToDescriberMap = new HashMap<>();
	private final SiteswapDescriber defaultDescriber;

	public DescriptionServiceImpl(final SiteswapDescriber fourHandedSiteswapDescriber,
	                              final SiteswapDescriber passingDescriber,
	                              final SiteswapDescriber defaultDescriber)
	{
		this.defaultDescriber = defaultDescriber;
		typeToDescriberMap.put(SiteswapType.FOUR_HANDED_SITESWAP, fourHandedSiteswapDescriber);
		typeToDescriberMap.put(SiteswapType.PASSING_SITESWAP, passingDescriber);
	}

	@Override
	public Optional<Description> describe(final Siteswap siteswap, final List<Locale.LanguageRange> priorityList)
	{
		return Optional.ofNullable(typeToDescriberMap.get(SiteswapType.getType(siteswap)))
			.map(siteswapDescriber -> siteswapDescriber.describe(siteswap, priorityList));
	}

	@Override
	public Map<Locale, Description> describe(final Siteswap siteswap)
	{
		final SiteswapDescriber describer = Optional.ofNullable(typeToDescriberMap.get(SiteswapType.getType(siteswap)))
			.orElse(defaultDescriber);

		return describer.getAvailableLocales().stream()
			.map(locale -> describer.describe(siteswap, locale))
			.collect(Collectors.toMap(Description::getLocale, Function.identity()));
	}

}
