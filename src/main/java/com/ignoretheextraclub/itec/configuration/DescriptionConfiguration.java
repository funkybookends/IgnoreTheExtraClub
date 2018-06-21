package com.ignoretheextraclub.itec.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.describer.SiteswapDescriber;
import com.ignoretheextraclub.siteswapfactory.describer.delegating.DelegatingDescriber;
import com.ignoretheextraclub.siteswapfactory.describer.delegating.LocaleIntersectioningDelegatingDescriber;
import com.ignoretheextraclub.siteswapfactory.describer.impl.SimpleFourHandedSiteswapDescriber;
import com.ignoretheextraclub.siteswapfactory.describer.impl.SimplePassingSiteswapDescriber;
import com.ignoretheextraclub.siteswapfactory.describer.impl.ToStringSiteswapDescriptionContributor;

@Configuration
public class DescriptionConfiguration
{
	private DescriptionContributor toStringContributor = new ToStringSiteswapDescriptionContributor();

	@Bean
	public SiteswapDescriber fourHandedSiteswapDescriber()
	{
		final DescriptionContributor fhsDescriber = new SimpleFourHandedSiteswapDescriber();

		return new LocaleIntersectioningDelegatingDescriber(Arrays.asList(toStringContributor, fhsDescriber));
	}

	@Bean
	public SiteswapDescriber passingDescriber()
	{
		final DescriptionContributor passingDescriber = new SimplePassingSiteswapDescriber();

		return new LocaleIntersectioningDelegatingDescriber(Arrays.asList(toStringContributor, passingDescriber));
	}

	@Bean
	public SiteswapDescriber defaultDescriber()
	{
		return new DelegatingDescriber(Collections.singletonList(toStringContributor), Arrays.asList(Locale.getAvailableLocales()));
	}
}
