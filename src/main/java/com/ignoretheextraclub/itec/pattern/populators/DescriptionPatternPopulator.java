package com.ignoretheextraclub.itec.pattern.populators;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ignoretheextraclub.itec.description.DescriptionService;
import com.ignoretheextraclub.itec.pattern.Pattern;
import com.ignoretheextraclub.itec.pattern.PatternPopulator;
import com.ignoretheextraclub.siteswapfactory.describer.Description;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

import lombok.extern.java.Log;

@Component
@Log
public class DescriptionPatternPopulator implements PatternPopulator
{
	private final DescriptionService descriptionService;

	@Autowired
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
		if (StringUtils.hasText(description.getLongDescription()))
		{
			builder.longDescription(description.getLocale(), description.getLongDescription());
		}

		if (StringUtils.hasText(description.getDescription()))
		{
			builder.shortDescription(description.getLocale(), description.getDescription());
		}

		if (description.getLocale().equals(LocaleContextHolder.getLocale()))
		{
			builder.title(description.getSiteswapName());
		}

		description.getSiteswapNames().forEach(builder::name);
	}
}
