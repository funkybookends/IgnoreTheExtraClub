package com.ignoretheextraclub.itec.configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ignoretheextraclub.itec.siteswap.SiteswapType;
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

@Configuration
public class SiteswapFactoriesConfiguration
{
	@Bean
	public Map<SiteswapType, SiteswapFactory> typedConstructors()
	{
		final Map<SiteswapType, SiteswapFactory> map = new HashMap<>();

		final FourHandedSiteswapFactory fhs = FourHandedSiteswapFactory.getDefault();
		final TwoHandedVanillaSiteswapFactory ths = TwoHandedVanillaSiteswapFactory.getDefault();
		final PassingSiteswapFactory passing = PassingSiteswapFactory.getDefault();

		map.put(SiteswapType.FOUR_HANDED_SITESWAP, fhs);
		map.put(SiteswapType.TWO_HANDED_SITESWAP, ths);
		map.put(SiteswapType.PASSING_SITESWAP, passing);

		return map;
	}

	@Bean
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

	@Bean
	public SiteswapRequestBuilder siteswapRequestBuilder()
	{
		return new SiteswapRequestBuilder();
	}

	private <V> void putValueWithKeys(final Map<String, V> map, final V value, final String... keys)
	{
		Stream.of(keys).forEach(key -> map.put(key, value));
	}
}
