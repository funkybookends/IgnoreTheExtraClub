package com.ignoretheextraclub.itec.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.ignoretheextraclub.itec.description.DescriptionService;
import com.ignoretheextraclub.itec.description.impl.DescriptionServiceImpl;
import com.ignoretheextraclub.itec.pattern.PatternPopulator;
import com.ignoretheextraclub.itec.pattern.PatternService;
import com.ignoretheextraclub.itec.pattern.impl.DescriptionPatternPopulator;
import com.ignoretheextraclub.itec.pattern.impl.IsGroundedSiteswapDetailsPatternPopulator;
import com.ignoretheextraclub.itec.pattern.impl.IsPrimeSiteswapDetailsPatternPopulator;
import com.ignoretheextraclub.itec.pattern.impl.NumHandsSiteswapDetailsPatternPopulator;
import com.ignoretheextraclub.itec.pattern.impl.NumJugglersSiteswapDetailsPatternPopulator;
import com.ignoretheextraclub.itec.pattern.impl.NumObjectsSiteswapDetailsPatternPopulator;
import com.ignoretheextraclub.itec.pattern.impl.PatternServiceImpl;
import com.ignoretheextraclub.itec.pattern.impl.SiteswapSiteswapDetailsPatternPopulator;
import com.ignoretheextraclub.itec.pattern.impl.TypePatternPopulator;
import com.ignoretheextraclub.itec.siteswap.SiteswapService;
import com.ignoretheextraclub.itec.siteswap.impl.SiteswapServiceImpl;
import com.ignoretheextraclub.itec.siteswap.impl.SiteswapType;
import com.ignoretheextraclub.siteswapfactory.describer.DescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.describer.SiteswapDescriber;
import com.ignoretheextraclub.siteswapfactory.describer.delegating.DelegatingDescriber;
import com.ignoretheextraclub.siteswapfactory.describer.delegating.LocaleIntersectioningDelegatingDescriber;
import com.ignoretheextraclub.siteswapfactory.describer.impl.SimpleFourHandedSiteswapDescriber;
import com.ignoretheextraclub.siteswapfactory.describer.impl.SimplePassingSiteswapDescriber;
import com.ignoretheextraclub.siteswapfactory.describer.impl.ToStringSiteswapDescriptionContributor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequestBuilder;
import com.ignoretheextraclub.siteswapfactory.factory.impl.FourHandedSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.factory.impl.PassingSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.factory.impl.SiteswapFactoryImpl;
import com.ignoretheextraclub.siteswapfactory.factory.impl.TwoHandedVanillaSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.constructors.StringToTwoHandedSyncSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StringToFourHandedSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StringToPassingSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StringToTwoHandedSiteswapConstructor;

import dagger.Module;
import dagger.Provides;

@Module
public class PatternModuleConfiguration
{
	@Provides
	@Singleton
	public DescriptionContributor toStringSiteswapDescriptionContributor()
	{
		return new ToStringSiteswapDescriptionContributor();
	}

	@Provides
	@Singleton
	@Named("fourHandedSiteswapDescriber")
	public SiteswapDescriber fourHandedSiteswapDescriber(final DescriptionContributor toStringSiteswapDescriptionContributor)
	{
		return new LocaleIntersectioningDelegatingDescriber(Arrays.asList(toStringSiteswapDescriptionContributor, new SimpleFourHandedSiteswapDescriber()));
	}

	@Provides
	@Singleton
	@Named("passingDescriber")
	public SiteswapDescriber passingDescriber(final DescriptionContributor toStringSiteswapDescriptionContributor)
	{
		return new LocaleIntersectioningDelegatingDescriber(Arrays.asList(toStringSiteswapDescriptionContributor, new SimplePassingSiteswapDescriber()));
	}

	@Provides
	@Singleton
	@Named("defaultDescriber")
	public SiteswapDescriber defaultDescriber(final DescriptionContributor toStringSiteswapDescriptionContributor)
	{
		return new DelegatingDescriber(Collections.singletonList(toStringSiteswapDescriptionContributor), Arrays.asList(Locale.getAvailableLocales()));
	}

	@Provides
	@Singleton
	public SiteswapFactory defaultSiteswapFactory()
	{
		final List<SiteswapConstructor<? extends Siteswap>> siteswapConstructors = Arrays.asList(
			StringToTwoHandedSiteswapConstructor.get(),
			StringToTwoHandedSyncSiteswapConstructor.get(),
			StringToFourHandedSiteswapConstructor.get(),
			StringToPassingSiteswapConstructor.get()
		);

		return new SiteswapFactoryImpl(siteswapConstructors);
	}

	@Provides
	@Singleton
	public DescriptionService descriptionService(@Named("fourHandedSiteswapDescriber") final SiteswapDescriber fourHandedSiteswapDescriber,
	                                             @Named("passingDescriber") final SiteswapDescriber passingDescriber,
	                                             @Named("defaultDescriber") final SiteswapDescriber defaultDescriber)
	{
		return new DescriptionServiceImpl(fourHandedSiteswapDescriber, passingDescriber, defaultDescriber);
	}

	@Provides
	@Singleton
	@Inject
	public List<PatternPopulator> patternPopulators(final DescriptionService descriptionService)
	{
		return Arrays.asList(
			// new CausalDiagramLinksPatternPopulator(),
			new DescriptionPatternPopulator(descriptionService),
			new IsGroundedSiteswapDetailsPatternPopulator(),
			new IsPrimeSiteswapDetailsPatternPopulator(),
			new NumHandsSiteswapDetailsPatternPopulator(),
			new NumJugglersSiteswapDetailsPatternPopulator(),
			new NumObjectsSiteswapDetailsPatternPopulator(),
			new SiteswapSiteswapDetailsPatternPopulator(),
			new TypePatternPopulator()
		);
	}

	@Provides
	@Singleton
	public SiteswapService siteswapService(final SiteswapFactory defaultSiteswapFactory)
	{
		return new SiteswapServiceImpl(defaultSiteswapFactory);
	}

	@Provides
	@Singleton
	public PatternService PATTERN_SERVICE(final SiteswapService siteswapService, final List<PatternPopulator> patternPopulators)
	{
		return new PatternServiceImpl(siteswapService, patternPopulators);
	}
}
