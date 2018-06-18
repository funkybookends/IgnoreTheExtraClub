package com.ignoretheextraclub.itec.pattern.populators;

import org.springframework.stereotype.Component;

import com.ignoretheextraclub.itec.pattern.Pattern;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

import lombok.extern.java.Log;

@Component
@Log
public class DefaultDescriptionDetailsPatternPopulator implements DescriptionDetailsPatternPopulator
{
	@Override
	public void populate(final Pattern.DescriptionDetails.DescriptionDetailsBuilder builder, final Siteswap siteswap)
	{
		log.warning("Not implemented");
	}
}
