package com.ignoretheextraclub.itec.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

import com.ignoretheextraclub.itec.description.DescriptionService;
import com.ignoretheextraclub.itec.description.impl.DescriptionServiceImpl;
import com.ignoretheextraclub.itec.pattern.PatternPopulator;
import com.ignoretheextraclub.itec.pattern.PatternService;
import com.ignoretheextraclub.itec.pattern.impl.CausalDiagramLinksPatternPopulator;
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

public class Configuration
{
	private static final SingletonProvider<DescriptionContributor> TO_STRING_CONTRIBUTOR = new SingletonProvider<>
		(ToStringSiteswapDescriptionContributor::new);

	private static final SingletonProvider<SiteswapDescriber> FOUR_HANDED_SITESWAP_DESCRIBER = new SingletonProvider<>(() ->
		new LocaleIntersectioningDelegatingDescriber(Arrays.asList(TO_STRING_CONTRIBUTOR.get(), new SimpleFourHandedSiteswapDescriber())));

	private static final SingletonProvider<SiteswapDescriber> PASSING_DESCRIBER = new SingletonProvider<>(() ->
		new LocaleIntersectioningDelegatingDescriber(Arrays.asList(TO_STRING_CONTRIBUTOR.get(), new SimplePassingSiteswapDescriber())));

	private static final SingletonProvider<SiteswapDescriber> DEFAULT_DESCRIBER = new SingletonProvider<>(() ->
		new DelegatingDescriber(Collections.singletonList(TO_STRING_CONTRIBUTOR.get()), Arrays.asList(Locale.getAvailableLocales())));

	private static final SingletonProvider<Map<SiteswapType, SiteswapFactory>> TYPED_CONSTRUCTORS = new SingletonProvider<>(() ->
	{
		final Map<SiteswapType, SiteswapFactory> map = new HashMap<>();

		final FourHandedSiteswapFactory fhs = FourHandedSiteswapFactory.getDefault();
		final TwoHandedVanillaSiteswapFactory ths = TwoHandedVanillaSiteswapFactory.getDefault();
		final PassingSiteswapFactory passing = PassingSiteswapFactory.getDefault();

		map.put(SiteswapType.FOUR_HANDED_SITESWAP, fhs);
		map.put(SiteswapType.TWO_HANDED_SITESWAP, ths);
		map.put(SiteswapType.PASSING_SITESWAP, passing);

		return map;
	});

	private static final SingletonProvider<SiteswapFactory> DEFAULT_SITESWAP_FACTORY = new SingletonProvider<>(() ->
	{
		final List<SiteswapConstructor<? extends Siteswap>> siteswapConstructors = Arrays.asList(
			StringToTwoHandedSiteswapConstructor.get(),
			StringToTwoHandedSyncSiteswapConstructor.get(),
			StringToFourHandedSiteswapConstructor.get(),
			StringToPassingSiteswapConstructor.get()
		);

		return new SiteswapFactoryImpl(siteswapConstructors);
	});

	private static final SingletonProvider<SiteswapRequestBuilder> SITESWAP_REQUEST_BUILDER = new SingletonProvider<>
		(SiteswapRequestBuilder::new);


	private static final SingletonProvider<DescriptionService> DESCRIPTION_SERVICE = new SingletonProvider<>(() ->
		new DescriptionServiceImpl(FOUR_HANDED_SITESWAP_DESCRIBER.get(), PASSING_DESCRIBER.get(), DEFAULT_DESCRIBER.get()));

	private static final SingletonProvider<List<PatternPopulator>> PATTERN_POPULATORS = new SingletonProvider<>(() ->
		Arrays.asList(
			// new CausalDiagramLinksPatternPopulator(),
			new DescriptionPatternPopulator(DESCRIPTION_SERVICE.get()),
			new IsGroundedSiteswapDetailsPatternPopulator(),
			new IsPrimeSiteswapDetailsPatternPopulator(),
			new NumHandsSiteswapDetailsPatternPopulator(),
			new NumJugglersSiteswapDetailsPatternPopulator(),
			new NumObjectsSiteswapDetailsPatternPopulator(),
			new SiteswapSiteswapDetailsPatternPopulator(),
			new TypePatternPopulator()
		));

	private static final SingletonProvider<SiteswapService> SITESWAP_SERVICE = new SingletonProvider<>(() ->
		new SiteswapServiceImpl(TYPED_CONSTRUCTORS.get(), DEFAULT_SITESWAP_FACTORY.get(), SITESWAP_REQUEST_BUILDER.get()));

	public static final SingletonProvider<PatternService> PATTERN_SERVICE = new SingletonProvider<>(() ->
		new PatternServiceImpl(SITESWAP_SERVICE.get(), PATTERN_POPULATORS.get()));

	public static class SingletonProvider<T>
	{

		private T singleton;
		private final Supplier<T> supplier;

		private SingletonProvider(final Supplier<T> supplier)
		{
			this.supplier = supplier;
		}

		public T get()
		{
			if (singleton == null) {
				singleton = supplier.get();
			}
			return singleton;
		}
	}
}
