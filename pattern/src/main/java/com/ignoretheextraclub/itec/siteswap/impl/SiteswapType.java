package com.ignoretheextraclub.itec.siteswap.impl;

import java.util.Arrays;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.factory.impl.FourHandedSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.factory.impl.PassingSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.factory.impl.SiteswapFactoryImpl;
import com.ignoretheextraclub.siteswapfactory.siteswap.sync.constructors.StringToTwoHandedSyncSiteswapConstructor;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors.StringToTwoHandedSiteswapConstructor;

public enum SiteswapType
{
	FOUR_HANDED_SITESWAP
		(
			FourHandedSiteswapFactory.getDefault()
		),
	TWO_HANDED_SITESWAP
		(
			new SiteswapFactoryImpl(Arrays.asList(
				StringToTwoHandedSiteswapConstructor.get(),
				StringToTwoHandedSyncSiteswapConstructor.get())
			)
		),
	PASSING_SITESWAP
		(
			PassingSiteswapFactory.getDefault()
		);

	private final SiteswapFactory siteswapFactory;

	SiteswapType(final SiteswapFactory siteswapFactory)
	{
		this.siteswapFactory = siteswapFactory;
	}

	public SiteswapFactory getSiteswapFactory()
	{
		return siteswapFactory;
	}
}
