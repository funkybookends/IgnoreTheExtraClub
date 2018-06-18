package com.ignoretheextraclub.itec.pattern;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ignoretheextraclub.itec.exception.UnknownPatternTypeException;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

@Component
public class PatternServiceImpl implements PatternService
{
	private final Map<String, SiteswapFactory> typedConstructors;
	private final SiteswapFactory defaultConstructor;
	private final SiteswapRequestBuilder siteswapRequestBuilder;
	private final List<PatternPopulator> patternPopulators;

	@Autowired
	public PatternServiceImpl(@Qualifier("typedConstructors") final Map<String, SiteswapFactory> typedConstructors,
	                          @Qualifier("defaultSiteswapFactory") final SiteswapFactory defaultSiteswapFactory,
	                          final SiteswapRequestBuilder siteswapRequestBuilder,
	                          final List<PatternPopulator> patternPopulators)
	{
		this.typedConstructors = typedConstructors;
		this.defaultConstructor = defaultSiteswapFactory;
		this.siteswapRequestBuilder = siteswapRequestBuilder;
		this.patternPopulators = patternPopulators;
	}

	@Override
	public Pattern getPattern(final String type, final String name) throws InvalidSiteswapException, UnknownPatternTypeException
	{
		return buildPattern(Optional.ofNullable(typedConstructors.get(type))
			.orElseThrow(UnknownPatternTypeException::new)
			.apply(siteswapRequestBuilder.createSiteswapRequest(name)));
	}

	private Pattern buildPattern(final Siteswap siteswap)
	{
		final Pattern.PatternBuilder patternBuilder = new Pattern.PatternBuilder();
		patternPopulators.forEach(patternPopulator -> patternPopulator.populate(patternBuilder, siteswap));
		return patternBuilder.build();
	}

	@Override
	public Pattern getPattern(final String name) throws InvalidSiteswapException
	{
		final Siteswap siteswap = defaultConstructor.apply(siteswapRequestBuilder.createSiteswapRequest(name));
		return buildPattern(siteswap);
	}
}
