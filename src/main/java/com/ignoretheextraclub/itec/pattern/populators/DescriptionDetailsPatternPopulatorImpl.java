package com.ignoretheextraclub.itec.pattern.populators;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ignoretheextraclub.itec.pattern.Pattern;
import com.ignoretheextraclub.itec.pattern.PatternPopulator;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

@Component
public class DescriptionDetailsPatternPopulatorImpl implements PatternPopulator
{
	private final List<DescriptionDetailsPatternPopulator> descriptionDetailsPatternPopulators;

	@Autowired
	public DescriptionDetailsPatternPopulatorImpl(final List<DescriptionDetailsPatternPopulator> descriptionDetailsPatternPopulators)
	{
		this.descriptionDetailsPatternPopulators = descriptionDetailsPatternPopulators;
	}

	@Override
	public void populate(final Pattern.PatternBuilder builder, final Siteswap siteswap)
	{
		final Pattern.DescriptionDetails.DescriptionDetailsBuilder descriptionDetailsBuilder = Pattern.DescriptionDetails.builder();
		descriptionDetailsPatternPopulators.forEach(populator -> populator.populate(descriptionDetailsBuilder, siteswap));
		builder.descriptionDetails(descriptionDetailsBuilder.build());
	}
}
