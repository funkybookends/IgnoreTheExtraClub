package com.ignoretheextraclub.itec.pattern.impl;

import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.ignoretheextraclub.itec.description.DescriptionService;
import com.ignoretheextraclub.itec.pattern.PatternPopulator;
import com.ignoretheextraclub.itec.ui.Pattern;
import com.ignoretheextraclub.siteswapfactory.describer.Description;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

import lombok.extern.java.Log;

@Log
public class DescriptionPatternPopulator implements PatternPopulator
{
	private final DescriptionService descriptionService;

	@Inject
	public DescriptionPatternPopulator(final DescriptionService descriptionService)
	{
		this.descriptionService = descriptionService;
	}

	@Override
	public void populate(final Pattern.PatternBuilder builder, final Siteswap siteswap)
	{
		final Map<Locale, Description> descriptions = descriptionService.describe(siteswap);

		descriptions.forEach((locale, description) -> populate(builder, description));
	}

	private void populate(final Pattern.PatternBuilder builder, final Description description)
	{
		if (StringUtils.isNotBlank(description.getLongDescription()))
		{
			builder.longDescription(description.getLocale(), description.getLongDescription());
		}

		if (StringUtils.isNotBlank(description.getDescription()))
		{
			builder.shortDescription(description.getLocale(), description.getDescription());
		}

		builder.title(description.getSiteswapName());
		description.getSiteswapNames().forEach(builder::name);
	}
}
