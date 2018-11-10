package com.ignoretheextraclub.itec.siteswap.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ignoretheextraclub.itec.exception.UnknownPatternTypeException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

public enum SiteswapType
{
	FOUR_HANDED_SITESWAP(Names.FOUR_HANDED_SITESWAP_NAME, "fhs", "FourHandedSiteswap", "Four Handed Siteswap", "FHS"),
	TWO_HANDED_SITESWAP(Names.TWO_HANDED_VANILLA_SITESWAP_NAME, "ths", "Two Handed Siteswap", "TwoHandedVanillaSiteswap", "TwoHandedSiteswap"),
	PASSING_SITESWAP(Names.PASSING_NAME, "passing")
	;

	private static final Map<String, SiteswapType> NAMES_TO_TYPE_MAP = buildConstantsMap();

	private static Map<String, SiteswapType> buildConstantsMap()
	{
		final Map<String, SiteswapType> map = new HashMap<>();

		Arrays.stream(values()).forEach(siteswapType -> siteswapType.getAllNames().forEach(name -> map.put(name, siteswapType)));

		return map;
	}

	private final String fullName;
	private final String localeName;
	private final List<String> otherNames;

	SiteswapType(final String fullName, final String localeName, final String... otherNames)
	{
		this.fullName = fullName;
		this.localeName = localeName;
		this.otherNames = Arrays.asList(otherNames);
	}

	public String getSiteswapName()
	{
		return fullName;
	}

	public String getLocaleName()
	{
		return localeName;
	}

	public List<String> getOtherNames()
	{
		return otherNames;
	}

	public static SiteswapType resolveType(final String type) throws UnknownPatternTypeException
	{
		return Optional.ofNullable(NAMES_TO_TYPE_MAP.get(type)).orElseThrow(UnknownPatternTypeException::new);
	}

	public static SiteswapType getType(final Siteswap siteswap) throws UnknownPatternTypeException
	{
		return resolveType(siteswap.getType());
	}

	public List<String> getAllNames()
	{
		final ArrayList<String> names = new ArrayList<>();

		names.add(fullName);
		names.add(localeName);
		names.addAll(otherNames);

		return names;
	}

	private static class Names
	{
		public static final String FOUR_HANDED_SITESWAP_NAME = "Four Handed Siteswap";
		public static final String TWO_HANDED_VANILLA_SITESWAP_NAME = "Two Handed Vanilla Siteswap";
		public static final String PASSING_NAME = "Synchronous Passing Siteswap";
	}
}
